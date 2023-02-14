@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.taskform

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

import com.tinyspace.compose.TinyTaskTheme


private val durationOptions = listOf<Duration>(
    30.toDuration(DurationUnit.MINUTES),
    60.toDuration(DurationUnit.MINUTES),
    90.toDuration(DurationUnit.MINUTES),
    120.toDuration(DurationUnit.MINUTES),
)

private val projectOptions = listOf(
    "Workout", "Personal", "Coding", "Teaching"
)

@Composable
fun TaskFormScreen(
    viewModel: TaskFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navigateBack: () -> Boolean,
) {

    val state = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TaskFormAppBar(
                navigateBack
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

                TaskDescription(
                    { title ->
                        viewModel.onEvent(TaskFormEvent.TitleInput(title))
                    },
                    { description ->
                        viewModel.onEvent(TaskFormEvent.DescriptionInput(description))
                    },
                    uiState = state.value
                )
                Box(Modifier.height(8.dp))
                Title(title = stringResource(R.string.tags))
                TagOptions(state.value.tags, onTagSelected = { tagOption ->
                    viewModel.onEvent(TaskFormEvent.OnTagSelected(tagOption))
                })
                Box(Modifier.height(16.dp))
                Title(stringResource(R.string.duration))
                DurationOptions(
                    selectedOption = state.value.durationOption,
                    onOptionSelected = { option ->
                        viewModel.onEvent(TaskFormEvent.DurationSelected(option))

                    }
                )
                Text("${state.value}")
            }

            Button(onClick = { viewModel.onEvent(TaskFormEvent.Create) }) {
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
    uiState: TaskFormUiState
) {
    Column(modifier = Modifier.padding(horizontal = 32.dp)) {
        //Title
        OutlinedTextField(
            value = uiState.title,
            onValueChange = { onTitleChanged(it) },
            label = { Text(stringResource(R.string.title)) },
            modifier = Modifier.fillMaxWidth()
        )
        //Description
        OutlinedTextField(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            value = uiState.description,
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
fun TagOptions(selectedTags: List<Tag>, onTagSelected: (index: Int) -> Unit) {

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        projectOptions.forEachIndexed { index, option ->
            InputChip(selected = selectedTags.contains(defaultTags[index]), onClick = {
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