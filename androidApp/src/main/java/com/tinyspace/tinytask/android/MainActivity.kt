package com.tinyspace.tinytask.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tinyspace.tinytask.android.ui.theme.TinyTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TinyTaskTheme {
                Surface(modifier = Modifier.fillMaxSize()){
                    TinyTaskApp()
                }
            }
        }
    }
}


@Composable
fun TinyTaskApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = onboard
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
        composable(home) { HomeView() }
        /*...*/
    }
}