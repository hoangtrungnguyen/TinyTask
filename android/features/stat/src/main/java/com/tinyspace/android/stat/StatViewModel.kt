package com.tinyspace.android.stat

import androidx.lifecycle.viewModelScope
import com.tinyspace.common.BaseViewModel
import com.tinyspace.shared.domain.CountTaskUseCase
import com.tinyspace.shared.domain.CountType
import com.tinyspace.shared.domain.GetTotalDurationTaskUseCase
import com.tinyspace.shared.domain.GetWeekDayTimeSpent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StatViewModel(
    val countTaskUseCase: CountTaskUseCase,
    val getTotalDurationTaskUseCase: GetTotalDurationTaskUseCase,
    val getWeekDayTimeSpent: GetWeekDayTimeSpent
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
            b,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), initialState)

    override val uiState: StateFlow<StatUIState> = modelState.map(StatVMState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            modelState.value.toUiState()
        )

    init {

        viewModelScope.launch {
            getWeekDayTimeSpent()
        }
    }

    override fun onEvent(event: StatEvent) {
        TODO("Not yet implemented")
    }


}

