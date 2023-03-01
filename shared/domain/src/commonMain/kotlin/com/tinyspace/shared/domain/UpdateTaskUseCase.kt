package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepository
import com.tinyspace.shared.domain.exception.InsertErrorException
import com.tinyspace.shared.domain.model.Task

class UpdateTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        try {
            taskRepository.setCompleted(task = task.toDTO())
        } catch (e: Exception) {
            throw InsertErrorException()
        }
    }
}