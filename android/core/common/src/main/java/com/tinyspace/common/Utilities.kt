package com.tinyspace.common

import androidx.compose.ui.graphics.Color
import kotlin.math.floor


fun Int.formatSeconds(): String {
    val remainingSeconds = this % 60
    val minutes = floor(this / 60.0).toInt()
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

fun String.fromHexToColor(): Color = Color(android.graphics.Color.parseColor("#$this"))

