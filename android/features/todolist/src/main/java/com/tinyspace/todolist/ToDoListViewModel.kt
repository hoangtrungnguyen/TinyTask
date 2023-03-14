package com.tinyspace.todolist

import androidx.lifecycle.viewModelScope
import com.tinyspace.common.BaseViewModel
import com.tinyspace.common.UiState
import com.tinyspace.common.ViewModelState
import com.tinyspace.shared.domain.GetRecentTaskUseCase
import com.tinyspace.shared.domain.model.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ToDoListViewModel(
    private val getRecentTasksUseCase: GetRecentTaskUseCase
) : KoinComponent,
    BaseViewModel<TodoListEvent, TodoListUiState, TodoListVM>() {

    override val initialState: TodoListVM = TodoListVM(
        emptyList(),
        isLoading = false,
        message = ""
    )

    override val modelState: MutableStateFlow<TodoListVM> = MutableStateFlow(initialState)
    override val uiState: StateFlow<TodoListUiState> = modelState.map(TodoListVM::toUiState)
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


data class TodoListVM(
    val tasks: List<Task>,
    val selectedId: String? = null,
    val isLoading: Boolean,
    val message: String,
) : ViewModelState<TodoListUiState> {
    override fun toUiState(): TodoListUiState {
        return TodoListUiState(
            tasks = tasks.map { task ->
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

data class TodoListUiState(
    val tasks: List<UiTask>,
    val isLoading: Boolean = false,
    val selectedTask: String? = null,
) : UiState

