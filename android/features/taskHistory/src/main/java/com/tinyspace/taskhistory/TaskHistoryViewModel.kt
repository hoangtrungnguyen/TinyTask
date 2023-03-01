package com.tinyspace.taskhistory

import androidx.lifecycle.viewModelScope
import com.tinyspace.common.BaseUiState
import com.tinyspace.common.BaseViewModel
import com.tinyspace.common.BaseViewModelState
import com.tinyspace.common.Event
import com.tinyspace.shared.domain.GetTaskPaginationUseCase
import com.tinyspace.shared.domain.model.Tag
import com.tinyspace.shared.domain.model.Task
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration

class TaskHistoryViewModel(
    getTaskPaginationUseCase: GetTaskPaginationUseCase
) : BaseViewModel<HistoryScreenEvent, HistoryUiState, HistoryVMState>() {
    override val initialState: HistoryVMState
        get() = HistoryVMState()


    override val modelState: MutableStateFlow<HistoryVMState> = MutableStateFlow(initialState)

    override val uiState: StateFlow<HistoryUiState>
        get() = modelState.map(HistoryVMState::toUiState).stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            modelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            val tasks = getTaskPaginationUseCase(1)
            modelState.update { state ->
                state.copy(tasks = tasks)
            }
        }

    }

    override fun onEvent(event: HistoryScreenEvent) {

    }


}


data class HistoryVMState(
    val tasks: List<Task> = emptyList()
) : BaseViewModelState<HistoryUiState> {
    override fun toUiState(): HistoryUiState {
        return HistoryUiState(
            tasks = tasks.map {
                with(it) {
                    TaskUi(
                        uuid,
                        title,
                        tags,
                        completed = completed != 0L,
                        duration,
                        description = description
                    )
                }
            }.toList()
        )
    }
}

data class HistoryUiState(
    val tasks: List<TaskUi>,
    val currentTask: TaskUi? = null
) : BaseUiState

data class TaskUi(
    val id: String,
    val title: String,
    val tags: List<Tag>,
    val completed: Boolean,
    val timeSpent: Duration,
    val description: String,
)

class HistoryScreenEvent : Event