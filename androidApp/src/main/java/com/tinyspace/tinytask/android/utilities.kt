package com.tinyspace.tinytask.android

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

fun NavHostController.navigateAndReplaceStartRoute(newHomeRoute: String) {
    popBackStack(graph.startDestinationId, true)
    graph.setStartDestination(newHomeRoute)
    navigate(newHomeRoute)
}


fun Int.formatSeconds(): String {
    val remainingSeconds = this % 60
    val minutes = floor(this / 60.0 ).toInt()
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

fun String.fromHexToColor() : Color = Color(android.graphics.Color.parseColor("#$this"))


fun elapsedTime(){

}