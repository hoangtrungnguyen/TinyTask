package com.tinyspace.android.stat

import com.tinyspace.common.BaseUiState
import com.tinyspace.common.BaseViewModel
import com.tinyspace.common.BaseViewModelState

class StatViewModel : BaseViewModel<StatEvent, StatUIState, StatVMState>() {

    override val initialState: StatVMState
        get() = TODO("Not yet implemented")
    override val modelState: kotlinx.coroutines.flow.MutableStateFlow<StatVMState>
        get() = TODO("Not yet implemented")
    override val uiState: kotlinx.coroutines.flow.StateFlow<StatUIState>
        get() = TODO("Not yet implemented")

    override fun onEvent(event: StatEvent) {
        TODO("Not yet implemented")
    }
}

data class StatVMState(
    val count: Int,
    val finished: Int
) : BaseViewModelState<StatUIState> {
    override fun toUiState(): StatUIState {
        TODO("Not yet implemented")
    }
}

data class StatUIState(
    val count: Int,
    val finishedCount: Int
) : BaseUiState