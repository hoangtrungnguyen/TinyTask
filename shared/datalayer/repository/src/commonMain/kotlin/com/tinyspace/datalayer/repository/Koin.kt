package com.tinyspace.datalayer.repository

import org.koin.dsl.module


val repositoryModules = module {
    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
}