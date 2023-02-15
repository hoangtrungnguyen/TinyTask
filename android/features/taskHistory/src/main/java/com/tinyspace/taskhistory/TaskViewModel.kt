package com.tinyspace.taskhistory

import androidx.lifecycle.ViewModel
import com.tinyspace.domain.model.Task
import kotlin.time.Duration

class TaskViewModel: ViewModel() {


}


private data class ViewModelState(
    val tasks: List<Task>
)

data class TaskScreenUiState(
    val tasks: List<TaskUi>,
    val currentTask: TaskUi
)

data class TaskUi(
    val id: String,
    val code: Int,
    val title: String,
    val tags: List<Tag>,
    val timeSpent: Duration
)

data class Tag(
    val tag: String,
    val code: Int
)