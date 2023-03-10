package com.tinyspace.taskform

import android.util.DisplayMetrics
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


val questions: List<Pair<String, String>> = listOf(
    Pair(
        "Highlight of the day",
        "Take a  moment to really think about what is the thing will make your day by answer these questions"
    ),
    Pair("Urgency", "What’s the most pressing thing I have to do today?"),
    Pair(
        "Satisfaction",
        "At the end of the day, which Highlight will bring me the most satisfaction?"
    ),
    Pair("Joy", "When I reflect on today, what will bring me the most joy?"),
)

@Composable
fun IntroFlow(
    step: Int,
    onNextStep: () -> Unit,
) {
    var timer by rememberSaveable {
        mutableStateOf(42)
    }

    val displayMetrics = DisplayMetrics()
    val height = displayMetrics.heightPixels
    val width = displayMetrics.widthPixels


    AnimatedBox(1080)


    Column(
        verticalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            questions[step].first,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )
        Box(modifier = Modifier.height(50.dp))
        Text(
            questions[step].second,
            textAlign = TextAlign.Center
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        ElevatedButton(
            onClick = {
                onNextStep()
                if (step == questions.size + 1) {
                    onNextStep()
                }
            }) {
            Text("Next")
        }

    }
}


@Composable
fun AnimatedBox(maxSize: Int) {

    var size by remember { mutableStateOf(maxSize.dp) }
    val transition = rememberInfiniteTransition()
    val animSize by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        Modifier
            .height(size)
            .background(
                color = Color.LightGray
            ),
        contentAlignment = Alignment.Center
    ) {

    }
    LaunchedEffect(animSize) {
        size = (maxSize.dp * animSize)
    }
}

@Composable
@Preview
fun IntroFlowPreview() {
    MaterialTheme {
        Surface(color = Color.White) {
            IntroFlow(
                2,
                {}
            )
        }
    }
}