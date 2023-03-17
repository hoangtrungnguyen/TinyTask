package com.tinyspace.android.stat

import com.tinyspace.common.UiState
import com.tinyspace.common.ViewModelState

data class StatUIState(
    val finished: Int,
    val totalDuration: TotalDurationUi
) : UiState

data class TotalDurationUi(
    val hour: Int,
    val minute: Int
)


data class StatVMState(
    val total: Int = 0,
    val finishedCount: Int = 0,
    val totalDuration: Int = 0,
    val hoursSpent: List<Float> = listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f)
) : ViewModelState<StatUIState> {

    override fun toUiState(): StatUIState {
        return StatUIState(
            finishedCount,
            TotalDurationUi(
                (totalDuration / 60).toInt(),
                (totalDuration % 60).toInt()
            )
        )
    }
}
