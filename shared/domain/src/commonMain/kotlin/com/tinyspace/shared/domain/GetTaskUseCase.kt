package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepository
import com.tinyspace.shared.domain.model.Task

class GetTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskId: String): Task {
        return Task.fromRepo(taskRepository.getById(taskId))
    }
}
