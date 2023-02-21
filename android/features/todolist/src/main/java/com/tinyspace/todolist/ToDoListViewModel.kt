package com.tinyspace.todolist

import androidx.lifecycle.viewModelScope
import com.tinyspace.common.BaseUiState
import com.tinyspace.common.BaseViewModel
import com.tinyspace.common.BaseViewModelState
import com.tinyspace.shared.domain.GetRecentTaskUseCase
import com.tinyspace.shared.domain.model.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ToDoListViewModel(
    private val getRecentTasksUseCase: GetRecentTaskUseCase
) : KoinComponent,
    BaseViewModel<TodoListEvent, UiState, ViewModelState>() {

    override val initialState: ViewModelState = ViewModelState(
        emptyList(),
        isLoading = false,
        message = ""
    )

    override val modelState: MutableStateFlow<ViewModelState> = MutableStateFlow(initialState)
    override val uiState: StateFlow<UiState> = modelState.map(ViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            modelState.value.toUiState()
        )


    init {

        viewModelScope.launch {
            modelState.update {
                it.copy(isLoading = true)
            }
            watchCurrentTasks().collectLatest {
                modelState.update { state ->
                    state.copy(tasks = it)
                }
            }
        }.invokeOnCompletion {
            modelState.update {
                it.copy(isLoading = false)
            }
        }
    }


    private fun watchCurrentTasks(): Flow<List<Task>> {
        return getRecentTasksUseCase.watchRecent()
    }

    override fun onEvent(event: TodoListEvent) {

        when (event) {
            is TodoListEvent.StartTask -> {
                modelState.let {
                    viewModelScope.launch {
                        it.update { it.copy(selectedId = event.id) }
                        delay(100)
                        it.update { it.copy(selectedId = null) }
                    }
                }
            }
            else -> {}
        }
    }

}


data class ViewModelState(
    val tasks: List<Task>,
    val selectedId: String? = null,
    val isLoading: Boolean,
    val message: String,
) : BaseViewModelState<UiState> {
    override fun toUiState(): UiState {
        return UiState(
            tasks.map { task ->
                UiTask(task.title, task.description, task.uuid,
                    task.tags.map { it.name })
            },
            selectedTask = selectedId,
        )
    }

}


data class UiTask(
    val title: String,
    val description: String,
    val taskId: String,
    val tags: List<String> = emptyList()
)

data class UiState(
    val tasks: List<UiTask>,
    val isLoading: Boolean = false,
    val selectedTask: String? = null,
) : BaseUiState

