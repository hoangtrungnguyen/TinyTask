@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package com.tinyspace.tinytask.android

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tinyspace.android.stat.StatScreen
import com.tinyspace.compose.TinyTaskTheme
import com.tinyspace.todolist.TodoListScreen

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
    onTaskClick: (taskId: String) -> Unit,
    onNavigateHistoryScreen: () -> Unit,
    onNavigateToTaskForm: () -> Unit,
    onNavigateToPaymentScreen: () -> Unit
) {
    var tab by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = { HomeTopAppBar(tab, onNavigateToPaymentScreen) },

        bottomBar = {
            HomeBottomAppBar(tab, onChangeTab = {
                tab = it
            })
        },
        floatingActionButton = {
            if (tab == 1) FloatingActionButton(onClick = { onNavigateToTaskForm() }) {
                Icon(painterResource(id = R.drawable.add_24), contentDescription = "Adding")
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {

            when (tab) {

                0 -> TodoListScreen(onTaskSelected = { id ->
                    onTaskClick(id)
                })
                1 -> StatScreen {
                    onNavigateHistoryScreen()
                }
                else -> throw IllegalArgumentException()
            }
        }
    }
}

@Composable
fun HomeTopAppBar(
    tab: Int,
    navigateToPaymentPage: () -> Unit
) {
    TopAppBar(
        title = {
            when (tab) {
                0 -> Text("Tiny Task")
                else -> Text("Tiny Task")
            }
        },
        actions = {
            AnimatedContent(targetState = tab) {
                when (it) {
                    0 -> Box(Modifier)
                    1 -> IconButton(onClick = {
                        navigateToPaymentPage()
                    }) {
                        Icon(Icons.Rounded.Star, contentDescription = "Donation")
                    }
                }
            }

        }
    )
}


@Composable
fun HomeBottomAppBar(tab: Int, onChangeTab: (tab: Int) -> Unit) {
    BottomAppBar {
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
        HomeView(onTaskClick = {}, onNavigateHistoryScreen = {}, {}) {

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkModePreview() {
    TinyTaskTheme {}
}

@Preview
@Composable
fun HomeTopAppBarPreview() {
    TinyTaskTheme {
        HomeTopAppBar(tab = 0) {}
    }
}