package com.tinyspace.tinytask.di

import com.tinyspace.datalayer.repository.Repository
import com.tinyspace.domain.SaveTaskUseCase
import com.tinyspace.datalayer.repository.TaskRepository

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


private val useCaseModules = module {
    factory {
        SaveTaskUseCase()
    }
}
val repositoryModule = module {
    single { TaskRepository() as Repository }
}

private val sharedModules = listOf(useCaseModules)


fun initKoin(koinAppDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        koinAppDeclaration()
        modules(sharedModules)
        modules(repositoryModule)
    }


