package com.tinyspace.taskform

import com.tinyspace.common.Event

sealed interface TaskFormEvent : Event {
    data class SelectDuration(val choice: Int) : TaskFormEvent
    data class SelectTag(val tagOption: Int) : TaskFormEvent
    data class InputTitle(val title: String) : TaskFormEvent
    data class InputDescription(val description: String) : TaskFormEvent
    object CreateTask : TaskFormEvent
    object Done : TaskFormEvent
    object NextStep : TaskFormEvent
}