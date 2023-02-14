package com.tinyspace.tinytask.di

import com.tinyspace.datalayer.repository.TaskRepository
import com.tinyspace.datalayer.repository.TaskRepositoryImpl
import com.tinyspace.domain.SaveTaskUseCase
import com.tinyspace.datalayer.repository.repositoryModules
import com.tinyspace.tinytask.Platform
import org.koin.core.KoinApplication

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


object Modules {
    val core = module {
        factory { Platform() }
    }

    val repositories = module {
        factory { TaskRepositoryImpl() }
    }

    val useCases = module {
        factory { SaveTaskUseCase(get()) }
    }

}


fun initKoin(koinAppDeclaration: Module = module {},
viewModel: Module = module {  }) : KoinApplication =
    startKoin {
        modules(
            Modules.repositories,
            Modules.useCases,
            koinAppDeclaration,
            viewModel
        )
    }


