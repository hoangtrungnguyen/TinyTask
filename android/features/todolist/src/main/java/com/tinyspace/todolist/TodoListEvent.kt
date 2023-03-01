package com.tinyspace.todolist

import com.tinyspace.common.Event

sealed interface TodoListEvent : Event {
    data class StartTask(val id: String) : TodoListEvent

}

sealed interface TodoListUiEvent {
    data class NavigateToTaskDetail(val taskId: String) : TodoListEvent
}