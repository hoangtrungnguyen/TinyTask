@file:OptIn(ExperimentalTextApi::class, ExperimentalTextApi::class, ExperimentalTextApi::class,
    ExperimentalTextApi::class
)

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tinyspace.tinytask.android.theme.TinyTaskTheme
import kotlin.math.PI
import kotlin.math.max

const val CircularIndicatorDiameter: Double = 100.0

@Composable
fun CounterView(time: String,
//                counter: @Composable () -> Unit,
                progress: Float = 0.0f,
                modifier: Modifier
                ) {

    Box(contentAlignment = Alignment.Center){
        CircularProgress(modifier, onBackground = MaterialTheme.colors.primary, background = MaterialTheme.colors.onPrimary)
        Text (time, style = MaterialTheme.typography.h4 )
    }

}

@Composable
fun CircularProgress(modifier: Modifier,
                     onBackground: Color,
                     background: Color,
                     progress: Float  = 0.2f,){
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(256.dp)
    ) {
        // Start at 12 O'clock
        val startAngle = 270f
        val sweep = progress * 360f

        val strokeWidth: Dp = 12.dp
        val stroke = with(16.dp) {
            Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
        }

        drawDeterminateCircularIndicator(startAngle, 360f, background, stroke)
        drawDeterminateCircularIndicator(startAngle, sweep, onBackground, stroke)
    }
}

//private fun DrawScope.drawString(time: String, textMeasurer: TextMeasurer){
//    val textLayoutResult: TextLayoutResult =
//        textMeasurer.measure(text = AnnotatedString(time))
//
//    val textSize = textLayoutResult.size
//
//
//    val x = (this.size.width)/2f - textSize.width/2f
//    val y = (this.size.height)/2f - textSize.height / 2f
//    drawText(textMeasurer, time,
//        topLeft = Offset(x, y ),
//    )
//}

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

private fun DrawScope.drawDeterminateCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) = drawCircularIndicator(startAngle, sweep, color, stroke)

private fun DrawScope.drawIndeterminateCircularIndicator(
    startAngle: Float,
    strokeWidth: Dp,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    // Length of arc is angle * radius
    // Angle (radians) is length / radius
    // The length should be the same as the stroke width for calculating the min angle

    val squareStrokeCapOffset =
        (180.0 / PI).toFloat() * ((strokeWidth / (CircularIndicatorDiameter.toFloat() / 2f)) / 2f).value

    // Adding a square stroke cap draws half the stroke width behind the start point, so we want to
    // move it forward by that amount so the arc visually appears in the correct place
    val adjustedStartAngle = startAngle + squareStrokeCapOffset

    // When the start and end angles are in the same place, we still want to draw a small sweep, so
    // the stroke caps get added on both ends and we draw the correct minimum length arc
    val adjustedSweep = max(sweep, 0.1f)

    drawCircularIndicator(adjustedStartAngle, adjustedSweep, color, stroke)
}

@Preview
@Composable
fun CounterViewPreview() {

    TinyTaskTheme {
        Surface {

//            while()
            CounterView(time = "23:00", modifier = Modifier, progress = 0.3f)
        }
    }
}