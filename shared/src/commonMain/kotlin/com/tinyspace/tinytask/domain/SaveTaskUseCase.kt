package com.tinyspace.tinytask.domain

import com.tinyspace.tinytask.domain.model.Task

class SaveTaskUseCase(
) {

    suspend operator fun invoke(task: Task){
//        taskRepository.save(task.taskDTO)
    }
}