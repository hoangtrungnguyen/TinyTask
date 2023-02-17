package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepositoryImpl
import com.tinyspace.shared.domain.exception.InsertErrorException
import com.tinyspace.shared.domain.model.Task

class GetTaskUseCase(
    private val taskRepository: TaskRepositoryImpl
) {

    //Task here is domain task
    suspend operator fun invoke(task: Task) {
        try {
            taskRepository.getById(task.uuid)
        } catch (e: Exception) {
            throw InsertErrorException()
        }
    }
}
