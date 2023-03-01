package com.tinyspace.todolist

import com.tinyspace.common.CommonEvent

sealed interface TodoListEvent : CommonEvent {
    data class StartTask(val id: String) : TodoListEvent

}

sealed interface TodoListUiEvent {
    data class NavigateToTaskDetail(val taskId: String) : TodoListEvent
}