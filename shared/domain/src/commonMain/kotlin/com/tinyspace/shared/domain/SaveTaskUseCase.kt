package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TagRepository
import com.tinyspace.datalayer.repository.TaskRepository
import com.tinyspace.shared.domain.exception.InsertErrorException
import com.tinyspace.shared.domain.model.Task

class SaveTaskUseCase(
    private val taskRepository: TaskRepository,
    private val tagRepository: TagRepository
) {

    suspend operator fun invoke(task: Task) {
        try {
            taskRepository.save(task = task.toDTO())
        } catch (e: Exception) {
            throw InsertErrorException()
        }
    }
}
