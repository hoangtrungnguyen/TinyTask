package com.tinyspace.shared.domain


import com.tinyspace.datalayer.repository.TaskRepository
import com.tinyspace.shared.domain.exception.InsertErrorException
import com.tinyspace.shared.domain.model.Tag
import com.tinyspace.shared.domain.model.Task
import com.tinyspace.shared.domain.model.Task.Companion.fromRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecentTaskUseCase(
    private val taskRepositoryImpl: TaskRepository,
) {

    val tags = mutableListOf<Tag>()
    suspend operator fun invoke() {
        try {
            taskRepositoryImpl.getRecentTasks()
        } catch (e: Exception) {
            throw InsertErrorException()
        }
    }

    fun watchRecent(): Flow<List<Task>> {

        return taskRepositoryImpl.watchRecentTask().map {
            it.map { task -> fromRepo(task) }
        }
    }
}