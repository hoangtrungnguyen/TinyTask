package com.tinyspace.tinytask.android

import androidx.navigation.NavHostController
import kotlin.math.floor

fun NavHostController.navigateAndReplaceStartRoute(newHomeRoute: String) {
    popBackStack(graph.startDestinationId, true)
    graph.setStartDestination(newHomeRoute)
    navigate(newHomeRoute)
}


fun Int.formatSeconds(): String {
    val minutes = floor(this / 60.0 ).toInt()
    val remainingSeconds = this % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}