package com.tinyspace.repository

import org.koin.dsl.module

private val repositoryModules = module {
    single {
        TaskRepository()
    }
}