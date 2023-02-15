@file:OptIn(ExperimentalMaterial3Api::class)

package com.tinyspace.todolist

import android.graphics.Paint
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tinyspace.compose.TinyTaskTheme
import java.util.*


@Composable
fun TodoListScreen(
    onPopBack: () -> Boolean = { false }
) {


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    Scaffold {

        Box(
            modifier = Modifier
                .padding(it).fillMaxSize()
        ) {
            GroupTask(
                onStart = {},
                onSkip = {},
                onEdit = {},
                onHorizontalChanged = { },
                screenSize = Size(screenWidth.toFloat(), screenHeight.toFloat())
            )
        }
    }
}


@Composable
fun GroupTask(
    onStart: (taskId: Int) -> Unit,
    onSkip: (taskId: Int) -> Unit,
    onEdit: (taskId: Int) -> Unit,
    onHorizontalChanged: (offset: Float) -> Unit = {},
    screenSize: Size = Size.Zero
) {
//    val width = screenSize.width / 4 * 3
//    val height = screenSize.height / 4 * 3
//    println("${width} - $height")
    val width = 300.dp
    val height = 500.dp

    var offset by remember { mutableStateOf(Offset.Zero) }


    Surface(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {

                detectDragGestures(
                    onDrag = { pointer, dragAmount ->

                    },
                    onDragEnd = {
//                        offset = Offset.Zero
                    },
                    onDragCancel = {
                        offset = Offset.Zero
                    }
                )
            }) {
        TaskItemCard(
            Modifier,
            Color.LightGray,
            width,
            height,
            position = 1,
            onStart = {}
        ) {
            Text("${width} - $height")
        }
        TaskItemCard(
            Modifier,
            Color.Gray,
            width,
            height,
            position = 2,
            onStart = {}
        ) {
            Text("${width} - $height")
        }
        TaskItemCard(
            Modifier
                .graphicsLayer(
                    translationY = offset.y
                )
                .offset(x = offset.y.dp),
            Color.DarkGray,
            width,
            height,
            offset = offset,
            position = 3,
            onStart = {}
        ) {
            Text("$width - $height")
            Box(contentAlignment = Alignment.Center) {
                Text("$offset")
            }
        }
    }
}

@Composable
fun TaskItemCard(
    modifier: Modifier,
    color: Color,
    width: Dp,
    height: Dp,
    offset: Offset = Offset.Zero,
    position: Int,
    onStart: (taskId: Int) -> Unit = {},
    onTouch: () -> Unit = {},
    onMove: (offset: Offset) -> Unit = {},
    onUnTouch: (offset: Offset) -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment =
        BiasAlignment(0.2f - 0.1f * position, -0.4f + 0.05f * position),

        ) {
        Surface(
            Modifier.size(
                width = width,
                height = height
            ),
            color = color
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                content()
                OutlinedButton(onClick = { onStart(2) }) {
                    Text("Start")
                }
            }
        }
    }
}


@Composable
@Preview
fun TaskItemCardPreview() {
    TinyTaskTheme {
        TaskItemCard(Modifier, Color.Gray, 200.dp, 300.dp, position = 0, onStart = {}) {

        }
    }
}

@Composable
@Preview(
    name = "Group Task Preview"
)
fun GroupTaskItemPreview() {
    TinyTaskTheme {
        GroupTask(
            {},
            {},
            {},
            screenSize = Size.Zero,
        )
    }
}

@Composable
@Preview(
    name = "Screen Preview"
)
fun TodoListPreview() {
    TinyTaskTheme {
        TodoListScreen {
            true
        }
    }
}