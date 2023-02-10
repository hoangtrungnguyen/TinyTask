package com.tinyspace.tinytask.android.ui.task_form

internal sealed interface TaskFormEvent {
    data class DurationSelected(val choice: Int) : TaskFormEvent
    data class ProjectSelected(val project: Int) :TaskFormEvent
    data class TitleInput(val title: String) :TaskFormEvent
    data class DescriptionInput(val description: String): TaskFormEvent
    object Create : TaskFormEvent
}