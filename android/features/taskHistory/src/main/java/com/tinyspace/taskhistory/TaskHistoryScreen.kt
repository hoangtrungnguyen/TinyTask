@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.taskhistory

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.compose.TagIcon
import com.tinyspace.compose.TinyTaskTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun TaskHistoryScreen(
    onTaskClick: (String) -> Unit,
    onNavigateBack: () -> Unit = {},
    viewModel: TaskHistoryViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("History")
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.Rounded.ArrowBack, "Back ")
                    }
                }
            )
        }
    ) {

        Column(Modifier.padding(it)) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp), content = {
                    items(state.value.tasks) { task ->
                        TaskItem(
                            onNavigate = {
                                onTaskClick(
                                    task.id
                                )
                            },
                            task = task
                        )
                    }
                })
        }
    }
}


@Composable
fun TaskItem(
    task: TaskUi,
    onNavigate: (taskId: String) -> Unit
) {
    var expanded by rememberSaveable {
        mutableStateOf(true)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxSize()

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painterResource(id = R.drawable.ic_computer),
                    contentDescription = "",
                    modifier = Modifier.size(44.dp)
                )

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Column {
                        Text(
                            stringResource(R.string.ui_design),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            stringResource(
                                id = R.string.time_spent,
                                task.timeSpent.inWholeHours,
                                task.timeSpent.inWholeMinutes
                            ), style = MaterialTheme.typography.labelSmall
                        )
                    }

                    Row {
                        for (tag in task.tags) {
                            TagIcon(title = tag.name)
                        }
                    }
                }
            }


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        expanded = !expanded
                    }) {
                    Icon(Icons.Rounded.ArrowDropDown, "")
                }
            }
        }

        AnimatedVisibility(
            visibleState = remember { MutableTransitionState(expanded) }
                .apply { targetState = !expanded },
            enter = slideInVertically(
                initialOffsetY = { -40 }
            ) + expandVertically(
                expandFrom = Alignment.Top
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            ) {
                Text(task.description)
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TasksTabPreview() {
    TinyTaskTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TaskHistoryScreen(onTaskClick = {})
        }
    }
}

//val previewModule = module {
//    factory { Hello(text = "preview hello text") }
//}
//
//@Preview
//@Composable
//fun HelloPreview() {
//    Koin(appDeclaration = { modules(previewModule) }) {
//        Hello()
//    }
//}