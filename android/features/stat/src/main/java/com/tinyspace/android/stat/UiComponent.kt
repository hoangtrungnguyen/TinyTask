package com.tinyspace.android.stat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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