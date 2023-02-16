package com.tinyspace.domain

import com.tinyspace.datalayer.repository.TaskRepositoryImpl
import com.tinyspace.domain.exception.InsertErrorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.tinyspace.domain.model.Task as TaskModel

class GetRecentTaskUseCase(
    private val taskRepositoryImpl: TaskRepositoryImpl
) {
    suspend operator fun invoke(task: TaskModel) {
        try {
            taskRepositoryImpl.getRecentTasks()
        } catch (e: Exception) {
            throw InsertErrorException()
        }
    }

    fun watchRecent(): Flow<List<TaskModel>> {
        return taskRepositoryImpl.watchRecentTask().map {
            it.map(TaskModel::fromDb)
        }
    }
}