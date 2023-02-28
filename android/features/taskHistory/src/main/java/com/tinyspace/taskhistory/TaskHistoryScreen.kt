@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.taskhistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.compose.TagIcon
import com.tinyspace.compose.TinyTaskTheme
import com.tinyspace.compose.home
import org.koin.androidx.compose.koinViewModel


@Composable
fun TaskHistoryScreen(
    onTaskClick: (String) -> Unit,
    onNavigateBack: () -> Unit = {},
    viewModel: TaskViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()


    Scaffold {

        Column(Modifier.padding(it)) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp), content = {
                    items(state.value.tasks) { task ->
                        TaskItem(
                            navigate = {
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
    navigate: (taskId: String) -> Unit,
    task: TaskUi
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp)
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
                    Text(
                        stringResource(R.string.ui_design),
                        style = MaterialTheme.typography.titleMedium
                    )
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
                Text(
                    stringResource(
                        id = R.string.time_spent,
                        task.timeSpent.inWholeHours,
                        task.timeSpent.inWholeMinutes
                    ), style = MaterialTheme.typography.labelSmall
                )
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        navigate(home)
                    }) {
                    Icon(painterResource(id = R.drawable.ic_play), "")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TasksTabPreview() {
    TinyTaskTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            TaskHistoryScreen(onTaskClick = {})
        }

    }
}