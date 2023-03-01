package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.repository.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun update(task: Task)

    suspend fun save(task: Task)

    suspend fun delete(task: Task)

    suspend fun getById(id: String): Task

    suspend fun getLimit(count: Int): List<Task>

    suspend fun countAll(): Long
    suspend fun countCompleted(): Long
    fun countCompletedAsFlow(): Flow<Long>
    suspend fun countUnfinished(): Long
    suspend fun getAllTask(): List<Task>

    suspend fun getTotalDuration(): Long

    fun getTotalDurationAsFlow(): Flow<Long>

    fun watchRecentTask(): Flow<List<Task>>
    suspend fun getRecentTasks(): List<Task>

    suspend fun setCompleted(task: Task)
    fun countAllAsFlow(): Flow<Long>
}