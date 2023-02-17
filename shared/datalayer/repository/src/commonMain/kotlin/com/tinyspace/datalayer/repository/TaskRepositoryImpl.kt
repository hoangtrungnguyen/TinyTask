package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.DatabaseHelper
import com.tinyspace.datalayer.local.db.Task
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val databaseHelper: DatabaseHelper
) : TaskRepository{

    override suspend fun save(task: Task) {
        databaseHelper.insertTask(
            task.id,
            task.title,
            task.description,
            0,
            task.createdDate,
            task.duration
        )
    }


    fun watchRecentTask(): Flow<List<Task>> = databaseHelper.watchRecentTask()
    suspend fun getRecentTasks(): List<Task> {
        return databaseHelper.getRecentTasks()
    }

    suspend fun setCompleted(task: Task) {
        return databaseHelper.setCompleted(task)
    }

    override suspend fun update(task: Task) {
    }

    override suspend fun delete(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: String): Task {
        return databaseHelper.getById(id)
    }

    override suspend fun getLimit(count: Int) {
        TODO("Not yet implemented")
    }
}