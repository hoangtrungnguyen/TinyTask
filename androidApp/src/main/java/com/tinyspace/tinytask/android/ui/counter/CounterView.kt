import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tinyspace.tinytask.android.ui.theme.TinyTaskTheme

@Composable
fun CounterView(
    modifier: Modifier = Modifier,
    time: String,
    progress: Float = 0.0f,
) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgress(
            modifier,
            progress = progress,
            primaryColor = MaterialTheme.colorScheme.primary,
            background = MaterialTheme.colorScheme.primaryContainer.copy(0.5f)
        )
        Text(time, style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun CircularProgress(
    modifier: Modifier,
    background: Color = Color.LightGray,
    progress: Float = 0.2f,
    primaryColor: Color,
) {
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(220.dp)
    ) {
        // Start at 12 O'clock
        val startAngle = 270f
        val sweep = progress * 360f

        val strokeWidth: Dp = 18.dp
        val stroke = with(24.dp) {
            Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        }
        drawDeterminateCircularIndicator(startAngle, 360f, background, stroke)
        drawDeterminateCircularIndicatorGradient(
            startAngle, sweep, color = primaryColor, stroke = stroke
        )
    }
}


private fun DrawScope.drawCircularIndicator(
    startAngle: Float, sweep: Float, color: Color, stroke: Stroke
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
        alpha = 1.0f,
        style = stroke,
        colorFilter = null,
        blendMode = DefaultBlendMode

    )
}

private fun DrawScope.drawCircularIndicatorGradient(
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
        blendMode = DefaultBlendMode

    )
}


private fun DrawScope.drawDeterminateCircularIndicator(
    startAngle: Float, sweep: Float, color: Color, stroke: Stroke
) = drawCircularIndicator(startAngle, sweep, color, stroke)

private fun DrawScope.drawDeterminateCircularIndicatorGradient(
    startAngle: Float, sweep: Float, stroke: Stroke, color: Color
) = drawCircularIndicatorGradient(startAngle, sweep, color, stroke)

@Preview
@Composable
fun CounterViewPreview() {

    TinyTaskTheme {
        Surface(color = Color.White) {
            CounterView(time = "23:00", modifier = Modifier, progress = 0.3f)
        }
    }
}