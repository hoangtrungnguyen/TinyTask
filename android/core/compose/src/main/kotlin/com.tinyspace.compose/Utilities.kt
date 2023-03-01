package com.tinyspace.compose

import androidx.compose.ui.graphics.Color


fun String.fromHexToColor() : Color = Color(android.graphics.Color.parseColor("#$this"))

