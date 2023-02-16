package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.DatabaseHelper
import com.tinyspace.datalayer.local.db.Task
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val databaseHelper: DatabaseHelper
) : TaskRepository{

    fun save(task: Task) {
        databaseHelper.insertTask(
            task.id,
            task.title,
            task.description,
            0,
            task.createdDate,
            task.duration
        )
    }

//    suspend fun getRecent(): List<Task>{
//        return databaseHelper.getRecentTasks()
//    }

    fun watchRecentTask(): Flow<List<Task>> = databaseHelper.watchRecentTask()
    suspend fun getRecentTasks(): List<Task> {
        return databaseHelper.getRecentTasks()
    }
}