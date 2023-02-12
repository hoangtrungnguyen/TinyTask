package com.tinyspace.taskform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class TaskFormViewModel(
//    final val saveTaskUseCase: SaveTaskUseCase
) :ViewModel(){

    private val initialState: ViewModelState = ViewModelState(0.toDuration(DurationUnit.MINUTES),"","", emptyList())

    private val modelState = MutableStateFlow(initialState)

    val uiState  = modelState.map(ViewModelState::toUiState)
        .stateIn(viewModelScope,
            SharingStarted.Eagerly,
            modelState.value.toUiState())


    private fun create(){
        viewModelScope.launch {
//            saveTaskUseCase(Any())
        }
    }


    internal fun onEvent(event: TaskFormEvent) = when(event){
        is TaskFormEvent.Create -> {
            create()
        }
        is TaskFormEvent.DescriptionInput -> {
            modelState.update {state ->
                state.copy(description = event.description)
            }
        }
        is TaskFormEvent.DurationSelected -> {
            modelState.update {state ->
                state.copy(
                    duration = durations[event.choice]
                )
            }
        }
        is TaskFormEvent.ProjectSelected -> TODO()
        is TaskFormEvent.TitleInput -> TODO()
    }
}


private data class ViewModelState(
    val duration: Duration,
    val title: String,
    val description: String,
    val tags : List<Tag>
){

    fun toUiState(): TaskFormUiState {
        return TaskFormUiState(duration)
    }
}


data class Tag(
    val name:String,
    val code: Int
)

data class TaskFormUiState(
    val duration: Duration?,

    ){

}


internal val tags = listOf(
    Tag("Work", 1),
    Tag("Personal", 2),
    Tag("Workout", 3),
    Tag("Coding", 4),
)


private val durations = listOf<Duration>(
    30.toDuration(DurationUnit.MINUTES),
    60.toDuration(DurationUnit.MINUTES),
    90.toDuration(DurationUnit.MINUTES),
    120.toDuration(DurationUnit.MINUTES),
)
