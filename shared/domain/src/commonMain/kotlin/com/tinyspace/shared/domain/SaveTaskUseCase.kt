package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.HighlightRepository
import com.tinyspace.datalayer.repository.TagRepository
import com.tinyspace.datalayer.repository.TaskRepository
import com.tinyspace.shared.domain.exception.InsertErrorException
import com.tinyspace.shared.domain.model.Task

class SaveTaskUseCase(
    private val taskRepository: TaskRepository,
    private val tagRepository: TagRepository,
    private val highlightRepository: HighlightRepository
) {

    suspend operator fun invoke(task: Task) {
        try {
            taskRepository.save(task = task.toDTO())
            highlightRepository.saveTodayHighlight(task.uuid, task.createdTime)
        } catch (e: Exception) {
            throw InsertErrorException()
        }
    }
}
