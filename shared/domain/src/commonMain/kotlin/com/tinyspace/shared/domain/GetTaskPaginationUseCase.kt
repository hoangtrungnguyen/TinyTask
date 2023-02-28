package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepository

class GetTaskPaginationUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(
        page: Int,
    ) {

    }
}