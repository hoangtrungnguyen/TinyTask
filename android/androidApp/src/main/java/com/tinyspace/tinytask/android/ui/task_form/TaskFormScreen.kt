@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.tinytask.android.ui.task_form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.tinytask.android.R
import com.tinyspace.tinytask.android.ui.theme.TinyTaskTheme
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


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

                GroupTextInput()
                Box(Modifier.height(8.dp))
                Title(title = stringResource(R.string.tags))
                GroupProjectOption()
                Box(Modifier.height(16.dp))
                Title(stringResource(R.string.duration))
                GroupCheckBox()
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
private fun GroupTextInput() {
    Column(modifier = Modifier.padding(horizontal = 32.dp)) {
        OutlinedTextField(value = "",
            onValueChange = {},
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = { Text("Description") }, maxLines = 10,
        )
    }

}


@Composable
private fun GroupCheckBox() {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(durationOptions[0]) }
    Row {
        durationOptions.forEachIndexed { index, it ->
            LabelledCheckbox(index = index, duration = it, selected = selectedOption == it) {
                onOptionSelected(durationOptions[it])
            }
        }
    }
}

@Composable
fun LabelledCheckbox(
    index: Int, duration: Duration, selected: Boolean, onSelected: (index: Int) -> Unit
) {
    Column(modifier = Modifier
        .padding(8.dp)
        .selectable(selected = selected, onClick = { onSelected(index) }),
        horizontalAlignment = Alignment.CenterHorizontally) {
        RadioButton(selected = selected, enabled = true, onClick = { onSelected(index) })
        Text(text = "${duration.inWholeMinutes}m")
    }
}


@Composable
fun GroupProjectOption() {

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        projectOptions.forEachIndexed { index, option ->
            InputChip(selected = true, onClick = { /*TODO*/ }, label = { Text(option) })
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