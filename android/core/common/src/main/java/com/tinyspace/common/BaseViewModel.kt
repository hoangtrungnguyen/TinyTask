package com.tinyspace.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<Event : com.tinyspace.common.Event, Ui : BaseUiState, State : BaseViewModelState<Ui>> :
    ViewModel() {

    abstract val initialState: State

    abstract val modelState: StateFlow<State>


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

    abstract fun onEvent(event: Event)

}

interface Event {
    interface VMEvent

    interface UiEvent
}


interface BaseViewModelState<Ui : BaseUiState> {
    fun toUiState(): Ui
}

interface BaseUiState