package com.tinyspace.datalayer.repository

import com.tinyspace.datalayer.local.model.Task

class TaskRepositoryImpl : TaskRepository{


    suspend fun save(task: Task){
        println("datalayer ${task}")
    }
}