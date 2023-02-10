package com.tinyspace.domain

import com.tinyspace.domain.model.Task
import com.tinyspace.repository.TaskRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject



class SaveTaskUseCase : KoinComponent {

    private val taskRepository: TaskRepository by inject()

    //Task here is domain task
    suspend operator fun invoke(task: Task){
//        taskRepository.save(task/*Task instance here is data layer task instance*/)
        println(task)
    }
}