package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepositoryImpl
import com.tinyspace.shared.domain.model.Task

class GetTaskUseCase(
    private val taskRepository: TaskRepositoryImpl
) {

    suspend operator fun invoke(taskId: String): Task {
        return Task.fromDb(taskRepository.getById(taskId))
    }
}
