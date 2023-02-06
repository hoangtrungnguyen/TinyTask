package com.tinyspace.tinytask.android

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.tinytask.android.theme.TinyTaskTheme


@Composable
fun HomeScreen(){
    var tab by rememberSaveable{
        mutableStateOf(0)
    }
    Scaffold(
        topBar = { HomeTopAppBar() },
        bottomBar = { HomeBottomAppBar(tab, onChangeTab = {
            tab = it
        })},
        floatingActionButton = {
            if(tab == 1) FloatingActionButton(onClick = { /*TODO*/ }) {

            }

        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            when(tab){
                0  -> TimerTab(tab != 0)
                1 -> TasksTab()
                else -> throw IllegalArgumentException()
            }
        }
    }
}

@Composable
fun HomeTopAppBar(){
    TopAppBar {
        Text("Tiny Tasks", style = MaterialTheme.typography.h5.copy(color = Color.White),
            modifier = Modifier.padding(
            horizontal = 8.dp
        ))
    }
}


@Composable
fun HomeBottomAppBar(tab: Int, onChangeTab: (tab: Int) -> Unit){
    BottomNavigation() {
        BottomNavigationItem(
            label = { Text("Timer") },
            icon  = { Icon(painter = painterResource(id = R.drawable.ic_timer_24), contentDescription = "timer")},
            onClick = {
                onChangeTab(0)
            },

            selected =  tab == 0,
        )
        BottomNavigationItem(
            icon  = { Icon(painter = painterResource(id = R.drawable.task_alt_24),
                contentDescription = "task")},
            onClick = {
                onChangeTab(1)
            },
            label = { Text("Tasks") },
            selected =  tab == 1,
        )
    }
}


@Preview
@Composable
fun HomeScreenPreview(){
    TinyTaskTheme {
        HomeScreen()
    }
}