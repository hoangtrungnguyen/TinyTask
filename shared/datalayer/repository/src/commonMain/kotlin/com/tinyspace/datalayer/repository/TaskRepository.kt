package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.db.Task

interface TaskRepository {
    suspend fun update(task: Task)

    suspend fun save(task: Task)

    suspend fun delete(task: Task)

    suspend fun getById(id: String)

    suspend fun getLimit(count: Int)


}