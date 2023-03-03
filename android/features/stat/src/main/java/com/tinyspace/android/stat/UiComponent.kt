@file:OptIn(ExperimentalTextApi::class, ExperimentalComposeUiApi::class)

package com.tinyspace.android.stat

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DurationText(hour: Int, minute: Int) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            "$hour", style =
            MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            "h", modifier = Modifier.padding(
                bottom = 2.dp
            )
        )
        Text(
            "$minute", style =
            MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            "m",
            modifier = Modifier.padding(
                bottom = 2.dp
            )
        )

    }

}


@Composable
fun ChartCanvas(
    modifier: Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>
) {

    val paint = Paint().asFrameworkPaint()

    val timeTextHeight = 48f
    val timeTextWidth = 156f

    val padding = 56f


    Canvas(
        modifier = modifier.background(
            Color.White
        )
    ) {

        val height = size.height
        val yUnit = (height - padding) / yValues.size
        val yStep = yUnit - timeTextHeight

        val width = size.width
        val xUnit = (width - padding - timeTextWidth) / xValues.size
        val xStep = xUnit - 100f


        paint.apply {
            isAntiAlias = true
            textSize = timeTextHeight
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        // Y axis
        drawIntoCanvas {
            for ((i, e) in yValues.withIndex()) {
//              it.nativeCanvas.drawText("10h00", padding,  height- ((i + 1) * (
//                       yStep)) , paint)

                val x = timeTextWidth + padding

                val y = height - yUnit * i - padding
                drawCircle(
                    color = Color.Red,
                    radius = 10f,
                    center = Offset(x, y)
                )
            }

        }
//
//        drawIntoCanvas {
//            for( (i, e) in xValues.withIndex()){
//                it.nativeCanvas.drawText("09h00",  (i + 1) * (
//                        xStep) , height - padding, paint)
//            }
//        }

//        drawIntoCanvas {
//            for (i in points.indices) {
//                val x1 = padding + (156) + xUnit * i
//                val y1 = size.height - (padding + 156 + yStep *
//                        (points[i]/yStep.toFloat()))
//                /** drawing circles to indicate all the points */
//                drawCircle(
//                    color = Color.Red,
//                    radius = 10f,
//                    center = Offset(x1,y1)
//                )
//            }
//        }
    }
}


@Composable
@Preview
fun ChartCanvasPreview() {
    MaterialTheme {
        ChartCanvas(
            Modifier
                .fillMaxWidth()
                .height(400.dp),
            xValues = listOf(0, 1, 2),
            yValues = listOf(9, 12, 1, 1),
            points = listOf(1f, 3f, 3f)
        )
    }
}