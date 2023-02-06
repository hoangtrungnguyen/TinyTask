package com.tinyspace.tinytask.android

import CounterView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinyspace.tinytask.android.theme.TinyTaskTheme

@Composable
fun TimerTab(isVisible: Boolean){

    AnimatedVisibility(isVisible,
        enter = fadeIn(initialAlpha = 0.3f),
        exit  = fadeOut(),

        ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Timer()
                Box(modifier = Modifier.height(16.dp))
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text("Start")
                }
            }
        }
    }
}


@Composable
fun Timer(){
    Surface() {
        CounterView(
            time = "22:00",
            modifier = Modifier,
            progress = 0.4f
        )
    }
}


@Preview
@Composable
fun TimerTabPreview(){
    TinyTaskTheme {
        TimerTab(false)
    }
}