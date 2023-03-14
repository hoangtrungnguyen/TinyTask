@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package com.tinyspace.taskform

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.compose.TinyTaskTheme
import com.tinyspace.shared.domain.model.Tag
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


private val durationOptions = listOf<Duration>(
    30.toDuration(DurationUnit.MINUTES),
    60.toDuration(DurationUnit.MINUTES),
    90.toDuration(DurationUnit.MINUTES),
    120.toDuration(DurationUnit.MINUTES),
)


//TODO Change to query from database later
private val projectOptions = listOf(
    "Workout", "Personal", "Coding", "Teaching"
)

@Composable
fun TaskFormScreen(
    isHighlight: Boolean = false,
    viewModel: TaskFormViewModel = koinViewModel {
        parametersOf(
            isHighlight
        )
    },
    navigateBack: () -> Boolean,
) {
    val snackBarNavHostState: SnackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.uiState.collectAsState()

    AnimatedContent(targetState = state.value.isLoading,
        transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 500)).with(
                fadeOut(animationSpec = tween(durationMillis = 500))
            )
        }) { isLoading ->

        if (isLoading) {
            CircularProgressIndicator()
            return@AnimatedContent
        }
        AnimatedContent(
            targetState = state.value.lastStep,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 500)).with(
                    fadeOut(animationSpec = tween(durationMillis = 500))
                )
            }
        ) { targetState ->
            if (targetState) {
                TaskFormUi(
                    snackBarNavHostState = snackBarNavHostState,
                    onEvent = {
                        viewModel.onEvent(it)
                    },
                    tagUis = state.value.tagUis,
                    title = state.value.title,
                    description = state.value.description,
                    durationOption = state.value.durationOption,
                    navigateBack = navigateBack
                )
            } else {
                IntroFlow(state.value.step) {
                    viewModel.onEvent(TaskFormEvent.NextStep)
                }
            }
        }


    }







    if (state.value.isDone) {
        navigateBack()
    }

}


@Composable
internal fun TaskFormUi(
    snackBarNavHostState: SnackbarHostState,
    onEvent: (event: TaskFormEvent) -> Unit,
    tagUis: List<Tag> = emptyList(),
    durationOption: Int,
    navigateBack: () -> Boolean,
    title: String,
    description: String
) {
    Scaffold(
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
                TagOptions(tagUis, onTagSelected = { tagOption ->
                    onEvent(TaskFormEvent.SelectTag(tagOption))
                })
                Box(Modifier.height(16.dp))
                Title(stringResource(R.string.duration))
                DurationOptions(selectedOption = durationOption,
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
        Text("TinyTask", style = MaterialTheme.typography.headlineMedium)
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
private fun DurationOptions(selectedOption: Int, onOptionSelected: (option: Int) -> Unit) {
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
fun TagOptions(selectedTagUis: List<Tag>, onTagSelected: (index: Int) -> Unit) {

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        projectOptions.forEachIndexed { index, option ->
            InputChip(selected = selectedTagUis.contains(defaultTagUis[index]), onClick = {
                onTagSelected(index)
            }, label = { Text(option) })
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