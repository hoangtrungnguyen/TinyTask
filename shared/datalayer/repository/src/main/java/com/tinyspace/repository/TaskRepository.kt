package com.tinyspace.repository

import com.tinyspace.local.Task
import com.tinyspace.local.TaskLocalSource

class TaskRepository internal constructor(
//    private val localSource : TaskLocalSource
){
    fun save(task: Task) {
        println("TaskRepository saved")
    }

}