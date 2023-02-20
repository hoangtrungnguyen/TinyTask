package com.tinyspace.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T : CommonEvent, Ui : BaseUiState, S : BaseViewModelState<Ui>> :
    ViewModel() {

    abstract val initialState: S

    abstract val modelState: StateFlow<S>


    /**
     * @see android/features/taskForm/src/main/java/com/tinyspace/taskform/TaskFormViewModel.kt

    Sample:

    val uiState : StateFlow<TaskFormUiState> = modelState.map(ViewModelState::toUiState)
    .stateIn(
    viewModelScope,
    SharingStarted.Eagerly,
    modelState.value.toUiState()
    )


     * **/
    abstract val uiState: StateFlow<Ui>

    abstract fun onEvent(event: T)

}

interface CommonEvent {
//    object NavigateBack: CommonEvent
}


interface BaseViewModelState<Ui : BaseUiState> {

    fun toUiState(): Ui


}

interface BaseUiState