@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.tinytask.android

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.tinyspace.tinytask.android.ui.counter.CounterTabScreen
import com.tinyspace.tinytask.android.ui.task.TasksTabScreen
import com.tinyspace.tinytask.android.ui.theme.TinyTaskTheme


@Composable
fun HomeView(

) {
    var tab by remember {
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
            if (tab == 1) FloatingActionButton(onClick = { }) {
                Icon(painterResource(id = R.drawable.add_24), contentDescription = "Adding")
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            when (tab) {
                0 -> CounterTabScreen(tab)
                1 -> TasksTabScreen()
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
                0 -> Text("Timer")
                else -> Text("Tiny Task")
            }
        }
    )
}


@Composable
fun HomeBottomAppBar(tab: Int, onChangeTab: (tab: Int) -> Unit) {
    BottomAppBar() {
        NavigationBarItem(
            label = { Text("Timer") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_timer_24),
                    contentDescription = "timer"
                )
            },
            onClick = {
                onChangeTab(0)
            },

            selected = tab == 0,
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.task_alt_24),
                    contentDescription = "task"
                )
            },
            onClick = {
                onChangeTab(1)
            },
            label = { Text("Tasks") },
            selected = tab == 1,
        )
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    TinyTaskTheme {
        HomeView()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkModePreview() {
    TinyTaskTheme {
        HomeView()
    }
}