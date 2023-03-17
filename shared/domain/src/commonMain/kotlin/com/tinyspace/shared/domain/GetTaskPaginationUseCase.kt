package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepository
import com.tinyspace.shared.domain.model.Task

class GetTaskPaginationUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(
        page: Int,
    ): List<Task> {
        return taskRepository.getLimit(10).map {
            Task.fromRepo(it)
        }
    }
}