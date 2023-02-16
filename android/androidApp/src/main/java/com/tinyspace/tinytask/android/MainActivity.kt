@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.tinyspace.tinytask.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tinyspace.compose.*
import com.tinyspace.taskform.TaskFormScreen
import com.tinyspace.taskhistory.TaskHistoryScreen
import com.tinyspace.tinytask.android.ui.counter.CounterScreen
import com.tinyspace.tinytask.android.ui.onboard.OnBoardingScreen
import com.tinyspace.todolist.TodoListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            TinyTaskTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TinyTaskApp(
                    )
                }
            }
        }
    }
}


@Composable
fun TinyTaskApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = onboard,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(onboard) {
            OnBoardingScreen(
                moveToNext = {
                    navController.navigateAndReplaceStartRoute(home)
                }
            )
        }

        composable(
            "$counter/{taskId}",
            arguments = listOf(navArgument("taskId") {
                type = NavType.StringType
            })
        ) {
            CounterScreen {
                navController.navigateUp()
            }
        }

        composable(home) {
            HomeView(
                onTaskClick = {
                    navController.navigate("$counter/${it}")
                },
            onNavigateHistoryScreen = {
                navController.navigate(history)
            }) {
                navController.navigate(taskForm)
            }
        }

        composable(taskForm){
            TaskFormScreen {
                navController.navigateUp()
            }
        }
        composable(history){
            TaskHistoryScreen(onTaskClick = {

            }, onNavigateBack = {
                navController.navigateUp()
            })
        }
        composable(todoList){
            TodoListScreen (onPopBack = {
                navController.navigateUp()
            }, onTaskSelected = {
                navController.navigate("$counter/${it}")
            })
        }

    }
}