package com.tinyspace.taskform

internal sealed interface TaskFormEvent {
    data class DurationSelected(val choice: Int) : TaskFormEvent
    data class OnTagSelected(val tagOption: Int) :TaskFormEvent
    data class TitleInput(val title: String) :TaskFormEvent
    data class DescriptionInput(val description: String): TaskFormEvent
    object Create : TaskFormEvent
    object Done : TaskFormEvent
}