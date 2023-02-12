package com.tinyspace.tinytask.android.ui.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.tinytask.android.R
import com.tinyspace.tinytask.android.common.TagIcon
import com.tinyspace.tinytask.android.home
import com.tinyspace.compose.TinyTaskTheme
import kotlin.time.DurationUnit
import kotlin.time.toDuration


val task = TaskUi(
    "2", 1, "2",
    listOf(Tag("Work", 2)),
    30.toDuration(DurationUnit.SECONDS)
)

@ExperimentalMaterial3Api
@Composable
fun TasksTabScreen(
    onTaskClick: (TaskUi) -> Unit
) {

    Scaffold(
    ) {
        Column(Modifier.padding(it)) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp), content = {
                    items(10) {
                        TaskItem(
                            navigate = {
                                onTaskClick(
                                    task
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
                        for(tag in task.tags){
                            TagIcon(title = tag.tag, code = tag.code)
                        }
                    }
                }
            }


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(task.timeSpent.toString(), style = MaterialTheme.typography.labelSmall)
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
            TasksTabScreen(onTaskClick = {})
        }

    }
}