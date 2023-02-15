package com.tinyspace.domain

import com.tinyspace.datalayer.repository.TaskRepositoryImpl
import com.tinyspace.domain.exception.DatabaseException
import com.tinyspace.domain.exception.InsertErrorException
import com.tinyspace.domain.model.Task as TaskModel

class SaveTaskUseCase(
    private val taskRepository: TaskRepositoryImpl
) {

    //Task here is domain task
    suspend operator fun invoke(task: TaskModel) {
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