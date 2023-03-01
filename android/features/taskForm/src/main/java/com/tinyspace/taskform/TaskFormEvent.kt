package com.tinyspace.taskform

internal sealed interface TaskFormEvent {
    data class SelectDuration(val choice: Int) : TaskFormEvent
    data class SelectTag(val tagOption: Int) : TaskFormEvent
    data class InputTitle(val title: String) : TaskFormEvent
    data class InputDescription(val description: String) : TaskFormEvent
    object CreateTask : TaskFormEvent
    object Done : TaskFormEvent
}