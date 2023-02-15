package com.tinyspace.tinytask.android.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tinyspace.tinytask.android.fromHexToColor



@Composable
fun RingIndicator(
    size: Float,
    primaryColor: Color = MaterialTheme.colorScheme.primary,

){
    Canvas(modifier = Modifier.size(size.dp)){

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

