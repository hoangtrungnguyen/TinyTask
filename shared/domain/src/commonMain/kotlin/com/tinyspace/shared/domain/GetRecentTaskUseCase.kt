package com.tinyspace.shared.domain


import com.tinyspace.datalayer.repository.TaskRepository
import com.tinyspace.shared.domain.exception.InsertErrorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.tinyspace.shared.domain.model.Task as TaskModel

class GetRecentTaskUseCase(
    private val taskRepositoryImpl: TaskRepository
) {
    suspend operator fun invoke() {
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