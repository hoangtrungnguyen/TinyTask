package com.tinyspace.common

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.floor


fun Int.formatSeconds(): String {
    val remainingSeconds = this % 60
    val minutes = floor(this / 60.0).toInt()
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

fun String.fromHexToColor(): Color = Color(android.graphics.Color.parseColor("#$this"))


fun <E : Event, Ui : UiState, VM : ViewModelState<Ui>> BaseViewModel<E, Ui, VM>.setMessage(
    vmStateFlow: MutableStateFlow<VM>, updateMessage: () -> Unit
) {
    viewModelScope.launch {

        vmStateFlow.update {
            it
        }
        delay(500)

        vmStateFlow.update {
            it
        }
    }
}

fun <Ui : UiState> ViewModelState<Ui>.updateMessage() {
}