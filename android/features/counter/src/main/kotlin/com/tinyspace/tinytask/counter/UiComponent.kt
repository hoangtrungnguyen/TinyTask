package com.tinyspace.tinytask.counter

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RingIndicator(
    size: Float,
    primaryColor: Color = MaterialTheme.colorScheme.primary,

    ) {
    Canvas(modifier = Modifier.size(size.dp)) {

        // Start at 12 O'clock
        val startAngle = 270f
        val sweep = 1.0f * 360f

        val strokeWidth: Dp = 3.dp
        val stroke = with(4.dp) {
            Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        }
        drawCircularIndicatorGradient(
            startAngle, sweep, color = primaryColor, stroke = stroke
        )

    }
}


fun DrawScope.drawCircularIndicatorGradient(
    startAngle: Float, sweep: Float, color: Color, stroke: Stroke
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset

    drawArc(
        brush = Brush.verticalGradient(
            colors = listOf(Color.White, color), startY = -size.height / 2
        ),
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        alpha = 1.0f,
        style = stroke,
        colorFilter = null,
        blendMode = DrawScope.DefaultBlendMode

    )
}
