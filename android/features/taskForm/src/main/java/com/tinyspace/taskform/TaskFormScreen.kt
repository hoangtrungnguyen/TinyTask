@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package com.tinyspace.taskform

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.compose.TinyTaskTheme
import com.tinyspace.shared.domain.model.Tag
import org.koin.androidx.compose.koinViewModel
import kotlin.time.Duration

@Composable
fun TaskFormScreen(
    viewModel: TaskFormViewModel = koinViewModel(),
    navigateBack: () -> Boolean,
) {
    val snackBarNavHostState: SnackbarHostState = remember { SnackbarHostState() }
    val uiState = viewModel.uiState.collectAsState()


    when (uiState.value) {
        is TaskFormUiState.Editing -> {
            TaskFormUi(
                snackBarNavHostState = snackBarNavHostState,
                onEvent = {
                    viewModel.onEvent(it)
                },
                uiState = (uiState.value) as TaskFormUiState.Editing,
                navigateBack = navigateBack,

                )
        }
        else -> {
            IntroFlow(
                (uiState.value as TaskFormUiState.Intro).step
            ) {
                viewModel.onEvent(TaskFormEvent.NextStep)
            }
        }
    }



    LaunchedEffect(uiState.value.isDone) {
        if (uiState.value.isDone) {
            navigateBack()
        }
    }

    LaunchedEffect(uiState.value.message) {
        if (uiState.value.message.isNotEmpty()) {
            snackBarNavHostState.showSnackbar(uiState.value.message)
        }
    }

    if (uiState.value.isLoading) {
        AlertDialog(
            onDismissRequest = { /* Dismiss the dialog */ },
            text = { Text(text = "Loading...") },
            confirmButton = {}
        )
    }

}


@Composable
internal fun TaskFormUi(
    uiState: TaskFormUiState.Editing,
    snackBarNavHostState: SnackbarHostState,
    onEvent: (event: TaskFormEvent) -> Unit,
    navigateBack: () -> Boolean,
) {

    val title = uiState.title
    val description = uiState.description
    val headLine = uiState.headline

    val selectedTag = uiState.selectedTag
    val tagOptions = uiState.tagOptions

    val durationOptions = uiState.durationOptions
    val selectedOption = uiState.selectedDuration

    val scale = animateFloatAsState(
        targetValue = 1f, // final scale value
        animationSpec = tween(durationMillis = 5000) // animation duration
    ).value

    val alpha = animateIntAsState(
        targetValue = 255, // final alpha value (opaque)
        animationSpec = tween(durationMillis = 5000) // animation duration
    ).value


    Scaffold(
        modifier = Modifier
            .scale(scale)
            .alpha(alpha / 255f),
        topBar = {
            TaskFormAppBar(
                navigateBack
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarNavHostState
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Header()
            Text(stringResource(id = headLine))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                TaskDescription({ title ->
                    onEvent(TaskFormEvent.InputTitle(title))
                }, { description ->
                    onEvent(TaskFormEvent.InputDescription(description))
                },

                    title = title,
                    description = description
                )
                Box(Modifier.height(8.dp))
                Title(title = stringResource(R.string.tags))
                TagOptions(
                    selectedTag, tagOptions, onTagSelected = { tagOption ->
                        onEvent(TaskFormEvent.SelectTag(tagOption))
                    })
                Box(Modifier.height(16.dp))
                Title(stringResource(R.string.duration))
                DurationOptions(
                    selectedOption = selectedOption,
                    durationOptions = durationOptions,
                    onOptionSelected = { option ->
                        onEvent(TaskFormEvent.SelectDuration(option))
                    })
            }

            Button(onClick = {
                onEvent(TaskFormEvent.CreateTask)
            }) {
                Text(stringResource(R.string.create))
            }
        }
    }

}


@Composable
private fun TaskFormAppBar(
    navigateBack: () -> Boolean,
) {
    TopAppBar(title = { Text("") }, actions = {
        IconButton(onClick = {
            navigateBack()

        }) {
            Icon(Icons.Rounded.Close, "close")
        }
    })
}

@Composable
private fun Header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            Icons.Rounded.Task,
            stringResource(R.string.task),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(56.dp)
        )
//        Text("TinyTask", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
private fun Title(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        textAlign = TextAlign.Start
    )

}

@Composable
private fun TaskDescription(
    onTitleChanged: (title: String) -> Unit,
    onDescriptionChanged: (description: String) -> Unit,
    title: String,
    description: String,
) {
    Column(modifier = Modifier.padding(horizontal = 32.dp)) {
        //Title
        OutlinedTextField(
            value = title,
            onValueChange = { onTitleChanged(it) },
            label = { Text(stringResource(R.string.title)) },
            modifier = Modifier.fillMaxWidth()
        )
        //Description
        OutlinedTextField(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            value = description,
            onValueChange = {
                onDescriptionChanged(it)
            },
            label = { Text("Description") }, maxLines = 10,
        )
    }

}


@Composable
private fun DurationOptions(
    selectedOption: Int,
    durationOptions: List<Duration>,
    onOptionSelected: (option: Int) -> Unit
) {
    Row {
        durationOptions.forEachIndexed { index, it ->
            LabelledCheckbox(index = index, duration = it, selected = selectedOption == index) {
                onOptionSelected(index)
            }
        }
    }
}

@Composable
fun LabelledCheckbox(
    index: Int, duration: Duration, selected: Boolean, onSelected: (index: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .selectable(selected = selected, onClick = { onSelected(index) }),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RadioButton(selected = selected, enabled = true, onClick = { onSelected(index) })
        Text(text = "${duration.inWholeMinutes}m")
    }
}


@Composable
fun TagOptions(selectedTag: Int?, tagOptions: List<Tag>, onTagSelected: (index: Int) -> Unit) {

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        tagOptions.forEachIndexed { index, option ->
            InputChip(selected = selectedTag == index, onClick = {
                onTagSelected(index)
            }, label = { Text(option.name) })
        }
    }

}

@Composable
@Preview
fun TaskFormScreenPreview() {
    TinyTaskTheme {
        TaskFormScreen {
            true
        }
    }
}