package com.tinyspace.shared.domain

import com.tinyspace.datalayer.repository.TaskRepositoryImpl
import com.tinyspace.shared.domain.exception.InsertErrorException
import com.tinyspace.shared.domain.model.Task

class SaveTaskUseCase(
    private val taskRepository: TaskRepositoryImpl
) {

    //Task here is domain task
    suspend operator fun invoke(task: Task) {
        try {
            taskRepository.save(task = task.toDTO())
        } catch (e: Exception) {
            throw InsertErrorException()
        }
    }
}

//private fun TaskModel.toDTO(): TaskDTO{
//    return TaskDTO(
//        uuid,
//        title,
//        description,
//        completed,
//        createdTime,
//        duration.toLong(DurationUnit.SECONDS)
//    )
//}