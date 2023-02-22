package com.tinyspace.android.stat

import androidx.lifecycle.viewModelScope
import com.tinyspace.common.BaseUiState
import com.tinyspace.common.BaseViewModel
import com.tinyspace.common.BaseViewModelState
import com.tinyspace.shared.domain.CountTaskUseCase
import com.tinyspace.shared.domain.CountType
import com.tinyspace.shared.domain.GetTotalDurationTaskUseCase
import kotlinx.coroutines.flow.*

class StatViewModel(
    val countTaskUseCase: CountTaskUseCase,
    val getTotalDurationTaskUseCase: GetTotalDurationTaskUseCase
) : BaseViewModel<StatEvent, StatUIState, StatVMState>() {

    override val initialState: StatVMState
        get() = StatVMState(0, 0, 0)
    override val modelState = combine(
        countTaskUseCase.invokeFlow(CountType.All),
        getTotalDurationTaskUseCase.invokeAsFlow()
    ) { a, b ->
        StatVMState(
            0,
            a,
            b
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), initialState)

    override val uiState: StateFlow<StatUIState> = modelState.map(StatVMState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            modelState.value.toUiState()
        )

    override fun onEvent(event: StatEvent) {
        TODO("Not yet implemented")
    }
}

data class StatVMState(
    val total: Int,
    val finished: Int,
    val totalDuration: Int
) : BaseViewModelState<StatUIState> {

    override fun toUiState(): StatUIState {
        return StatUIState(
            finished,
            TotalDurationUi(
                (totalDuration / 60).toInt(),
                (totalDuration % 60).toInt()
            )
        )
    }


}

data class StatUIState(
    val finished: Int,
    val totalDuration: TotalDurationUi
) : BaseUiState

data class TotalDurationUi(
    val hour: Int,
    val minute: Int
)