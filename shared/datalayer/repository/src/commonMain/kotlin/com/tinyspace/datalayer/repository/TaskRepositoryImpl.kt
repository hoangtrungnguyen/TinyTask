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


    override fun watchRecentTask(): Flow<List<Task>> = databaseHelper.watchRecentTask()
    override suspend fun getRecentTasks(): List<Task> {
        return databaseHelper.getRecentTasks()
    }

    override suspend fun setCompleted(task: Task) {
        return databaseHelper.setCompleted(task)
    }

    override fun countAllAsFlow(): Flow<Long> {
        return databaseHelper.countAllAsFlow()
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

    override suspend fun countAll(): Long {
        return databaseHelper.countAll()
    }

    override suspend fun countCompleted(): Long {
        return databaseHelper.countCompleted()
    }

    override fun countCompletedAsFlow(): Flow<Long> {
        return databaseHelper.countCompletedAsFlow()
    }

    override suspend fun countUnfinished(): Long {
        return databaseHelper.countUnfinished()
    }

    override suspend fun getAllTask(): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun getTotalDuration(): Long {
        return databaseHelper.countTotalDuration().SUM ?: -1L
    }

    override fun getTotalDurationAsFlow(): Flow<Long> {
        return databaseHelper.countCompletedAsFlow()
    }
}