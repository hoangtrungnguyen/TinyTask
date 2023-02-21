@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.todolist

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import coil.compose.AsyncImage
import com.tinyspace.compose.TagIcon
import com.tinyspace.compose.TinyTaskTheme
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Composable
fun TodoListScreen(
    onPopBack: () -> Boolean = { false },
    viewModel: ToDoListViewModel = koinViewModel(),
    onTaskSelected: (taskId: String) -> Unit,
) {


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val state = viewModel.uiState.collectAsState()


    val lifecycle = LocalLifecycleOwner.current.lifecycle


    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(), contentAlignment = BiasAlignment(0f, -0.35f)
        ) {
            LazyRow(
                Modifier.padding(),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(state.value.tasks) {
                    TaskItemCard(it.title, it.description, it.taskId, it.tags) { id ->
                        viewModel.onEvent(TodoListEvent.StartTask(id))
                    }
                }
            }
        }

    }

    LaunchedEffect(viewModel, lifecycle) {
        // Whenever the uiState changes, check if the user is logged in and
        // call the `onUserLogin` event when `lifecycle` is at least STARTED
        snapshotFlow { state.value }
            .filter { it.selectedTask != null }
            .map { it.selectedTask }
            .flowWithLifecycle(lifecycle)
            .collect {
                assert(it != null)
                it?.let {
                    onTaskSelected(it)
                }
            }
    }
}

@Composable
private fun TaskItemCard(
    title: String,
    description: String,
    taskId: String,
    tags: List<String>,
    onStart: (id: String) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.size(
            width = 350.dp,
            height = 600.dp,
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Red,

            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),

        ) {

        Surface {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1676202731475-afd55cddb97a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=627&q=80",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.background,
                    if (isSystemInDarkTheme()) BlendMode.Lighten else BlendMode.Darken
                ),
                alpha = 0.4f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 16.dp
                    ),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge
                )

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    IconButton(onClick = { onStart(taskId) }) {
                        Icon(
                            imageVector = Icons.Rounded.PlayCircle, contentDescription = "Start",
                            modifier = Modifier.size(80.dp),
                        )
                    }
                }
                Column {
                    Text(description)
                    Row {
                        Text("Tag")
                        for (name in tags) {
                            println("TAFFFFFFF $name")
                            TagIcon(title = name)
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview(
    name = "Screen Preview"
)
fun TodoListPreview() {
    TinyTaskTheme {
//        TodoListScreen({},{})
    }
}