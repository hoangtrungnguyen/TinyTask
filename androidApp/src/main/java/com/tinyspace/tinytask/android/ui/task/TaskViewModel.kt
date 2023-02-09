package com.tinyspace.tinytask.android.ui.task

import androidx.lifecycle.ViewModel
import kotlin.time.Duration

class TaskViewModel: ViewModel() {


    fun loadTasks(){

    }

}



data class TaskUiState(
    val tasks: List<Task>,
    val currentTask: Task
)

data class Task(
    val code: Int,
    val title: String,
    val tags: List<Tag>,
    val timeSpent: Duration
)

data class Tag(
    val tag: String,
    val code: Int
)