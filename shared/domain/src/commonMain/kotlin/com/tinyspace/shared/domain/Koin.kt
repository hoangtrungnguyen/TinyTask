package com.tinyspace.shared.domain

import org.koin.dsl.module

class Koin {
    companion object {
        val useCases = module {
            factory { GetRecentTaskUseCase(get()) }
            factory { SaveTaskUseCase(get()) }
            factory { UpdateTaskUseCase(get()) }
            factory { GetTaskUseCase(get()) }
        }
    }
}