package com.tinyspace.compose

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
import androidx.compose.ui.unit.dp


@Composable
fun TagIcon(title: String, code: Int) {
    return Box(

        modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.clip(RoundedCornerShape(6.dp)),
            color = "FFEFF1".fromHexToColor(),
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                text = title, color = Color.Red, style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
            )
        }

    }
}