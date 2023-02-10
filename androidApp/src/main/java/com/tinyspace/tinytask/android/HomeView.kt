@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.tinytask.android
import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tinyspace.tinytask.android.ui.stat.StatScreen
import com.tinyspace.tinytask.android.ui.task.TaskUi
import com.tinyspace.tinytask.android.ui.task.TasksTabScreen
import com.tinyspace.tinytask.android.ui.theme.TinyTaskTheme



class Destination(
    val nameId: Int,
    val iconId: Int
)

val destinations = listOf<Destination>(
    Destination(R.string.tasks, R.drawable.task_alt_24),
    Destination(R.string.stat, R.drawable.ic_bar_chart_24),
)

@Composable
fun HomeView(
    onTaskClick: (TaskUi) -> Unit,
    navigateToTaskForm: () -> Unit
) {
    var tab by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = { HomeTopAppBar(tab) },
        bottomBar = {
            HomeBottomAppBar(tab, onChangeTab = {
                tab = it
            })
        },
        floatingActionButton = {
            if (tab == 0) FloatingActionButton(onClick = { navigateToTaskForm() }) {
                Icon(painterResource(id = R.drawable.add_24), contentDescription = "Adding")
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {

            when (tab) {
                0 -> TasksTabScreen(onTaskClick = onTaskClick)
                1 -> StatScreen()
                else -> throw IllegalArgumentException()
            }
        }
    }
}

@Composable
fun HomeTopAppBar(tab: Int) {
    TopAppBar(
        title = {
            when(tab){
                0 -> Text("Tiny Task")
                else -> Text("Tiny Task")
            }
        }
    )
}


@Composable
fun HomeBottomAppBar(tab: Int, onChangeTab: (tab: Int) -> Unit) {
    BottomAppBar() {
        destinations.mapIndexed { index, value ->
            NavigationBarItem(
                label = { Text(stringResource(value.nameId)) },
                icon = {
                    Icon(
                        painter = painterResource(id = value.iconId),
                        contentDescription = stringResource(value.nameId)
                    )
                },
                onClick = {
                    onChangeTab(index)
                },

                selected = tab == index,
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    TinyTaskTheme {
        HomeView(onTaskClick = {}) {

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkModePreview() {
    TinyTaskTheme {
        HomeView(onTaskClick = {}) {
        }
    }
}