package com.tinyspace.domain

import com.tinyspace.datalayer.repository.TaskRepositoryImpl
import com.tinyspace.datalayer.local.model.Task as TaskDTO
import com.tinyspace.domain.model.Task

class SaveTaskUseCase (
    private val taskRepository: TaskRepositoryImpl
    ) {

    //Task here is domain task
    suspend operator fun invoke(task: Task){
        taskRepository.save(task = TaskDTO("dd"))
        println("SaveTaskUseCase ${task}")
    }
}