package com.tinyspace.todolist.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.tinyspace.compose.TinyTaskTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


//@Composable
//fun TodoListScreen2(
//    onPopBack: () -> Boolean
//) {
//
//
//    val configuration = LocalConfiguration.current
//    val screenWidth = configuration.screenWidthDp
//    val screenHeight = configuration.screenHeightDp
//
//    Scaffold {
//
//        Box(
//            modifier = Modifier
//                .padding(it)
//        ) {
//            GroupTask(
//                onStart = {},
//                onSkip = {},
//                onEdit = {},
//                onHorizontalChanged = { },
//                screenSize = Size(screenWidth.toFloat(), screenHeight.toFloat())
//            )
//        }
//    }
//}


@Composable
fun GroupTask(
    onStart: (taskId: Int) -> Unit,
    onSkip: (taskId: Int) -> Unit,
    onEdit: (taskId: Int) -> Unit,
    onHorizontalChanged: (offset: Float) -> Unit = {},
    screenSize: Size = Size.Zero
) {
    val width = 300.dp
    val height = 500.dp

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var animOffset by remember { mutableStateOf(Offset.Zero) }

    Surface(
        Modifier
        /*.swipeMotionBuilder(
            onOffsetXChanged = { offsetX = it },
            onOffsetYChanged = { offsetY = it },
            onAnimOffsetChanged = { animOffset = it }
        ) */
    ) {
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
            Modifier,
            Color.DarkGray,
            width,
            height,
            position = 3,
            onStart = {}
        ) {
            Text("$width - $height")
            Box(contentAlignment = Alignment.Center) {
                Text("Offset x $offsetX - y $offsetY")
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
    position: Int,
    onStart: (taskId: Int) -> Unit = {},
    onTouch: () -> Unit = {},
    onMove: (offset: Offset) -> Unit = {},
    onUnTouch: (offset: Offset) -> Unit = {},
    content: @Composable () -> Unit
) {

    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        offset += offsetChange
    }

//    var alignment: Alignment =
//        baseAlignment ?: BiasAlignment(0.2f - 0.1f * position, -0.4f + 0.05f * position)
//

    Box(
        modifier = modifier
            .size(
                width,
                height
            )
            .background(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ),
    )
    {

        Surface(color = color, modifier = modifier.fillMaxSize()) {
            content()
        }
    }
}


@Composable
@Preview
fun TaskItemCardPreview2() {
    TinyTaskTheme {
        TaskItemCard(Modifier, Color.Red, 200.dp, 300.dp, position = 0, onStart = {}) {

        }
    }
}

@Composable
@Preview(
    name = "Group Task Preview"
)
fun GroupTaskItemPreview2() {
    TinyTaskTheme {
        GroupTask(
            {},
            {},
            {},
            screenSize = Size.Zero,
        )
    }
}


fun Modifier.swipeMotionBuilder(
    onOffsetXChanged: (Float) -> Unit,
    onOffsetYChanged: (Float) -> Unit,
    onAnimOffsetChanged: (Offset) -> Unit,
): Modifier {
    var offsetX = 0f
    var offsetY = 0f
    var animOffset = Offset.Zero

    return this.then(offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        .graphicsLayer { translationX = animOffset.x; translationY = animOffset.y }
        .pointerInput(Unit) {
            forEachGesture {
                awaitPointerEventScope {
                    var startOffset = Offset.Zero
                    var prevOffset = Offset.Zero
                    var dragInProgress = false

                    awaitFirstDown()


                    do {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Press -> {
                                startOffset = event.calculatePan()
                                prevOffset = startOffset
                                dragInProgress = true
                            }

                            PointerEventType.Move -> {
                                val currentOffset = event.changes[0].position
                                offsetX += currentOffset.x - prevOffset.x

                                offsetX
                                    .run {
                                        this + (currentOffset.x - prevOffset.x)
                                    }
                                    .also {
                                        onOffsetXChanged(it)
                                    }

                                offsetY += currentOffset.y - prevOffset.y
                                prevOffset = currentOffset
                            }
                            PointerEventType.Release -> {
                                dragInProgress = false
                            }
                        }

                    } while (dragInProgress)

                }
            }
        })
}


@Preview
@Composable
fun DraggableBoxPreview() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var animOffset by remember { mutableStateOf(Offset.Zero) }

    Box(
        Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }

    ) {
        // Box content
        Text("Hii")
    }

}


private fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    // This Animatable stores the horizontal offset for the element.
    val offsetX = remember { Animatable(0f) }

    println("Offset $offsetX")
    pointerInput(Unit) {
        // Used to calculate a settling position of a fling animation.
        val decay = splineBasedDecay<Float>(this)
        // Wrap in a coroutine scope to use suspend functions for touch events and animation.
        coroutineScope {
            while (true) {
                // Wait for a touch down event.
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                // Interrupt any ongoing animation.
                offsetX.stop()
                // Prepare for drag events and record velocity of a fling.
                val velocityTracker = VelocityTracker()
                // Wait for drag events.
                awaitPointerEventScope {
                    horizontalDrag(pointerId) { change ->
                        // Record the position after offset
                        val horizontalDragOffset = offsetX.value + change.positionChange().x
                        launch {
                            // Overwrite the Animatable value while the element is dragged.
                            offsetX.snapTo(horizontalDragOffset)
                        }
                        // Record the velocity of the drag.
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                        // Consume the gesture event, not passed to external
                        change.consume()
                    }
                }
                // Dragging finished. Calculate the velocity of the fling.
                val velocity = velocityTracker.calculateVelocity().x
                // Calculate where the element eventually settles after the fling animation.
                val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)
                // The animation should end as soon as it reaches these bounds.
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )
                launch {
                    if (targetOffsetX.absoluteValue <= size.width) {
                        // Not enough velocity; Slide back to the default position.
                        offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                    } else {
                        // Enough velocity to slide away the element to the edge.
                        offsetX.animateDecay(velocity, decay)
                        // The element was swiped away.
                        onDismissed()
                    }
                }
            }
        }
    }
        // Apply the horizontal offset to the element.
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
}