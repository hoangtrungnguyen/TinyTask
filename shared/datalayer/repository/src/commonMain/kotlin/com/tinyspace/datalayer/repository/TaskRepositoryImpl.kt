package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.DatabaseHelper
import com.tinyspace.datalayer.local.model.Task

class TaskRepositoryImpl(
    private val databaseHelper: DatabaseHelper
) : TaskRepository{

    suspend fun save(task: Task){
        databaseHelper.insertTask(task.id,
            task.title,
            task.description,
            0,
            task.createdTime,
            task.duration
        )
    }
}