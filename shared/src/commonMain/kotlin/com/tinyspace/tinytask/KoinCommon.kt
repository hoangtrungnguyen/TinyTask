package com.tinyspace.tinytask

import com.tinyspace.datalayer.local.DatabaseHelper
import com.tinyspace.datalayer.repository.TaskRepositoryImpl
import com.tinyspace.shared.domain.Koin
import org.koin.core.KoinApplication

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module



//include sql driver module
expect val platformModule: Module

object Modules {
    val core = module {
        factory { Platform() }
        factory { DatabaseHelper(get()) }
    }

    val repositories = module {
        factory { TaskRepositoryImpl(get()) }
    }

    val useCases = Koin.useCases
}




fun initKoin(koinAppDeclaration: Module = module {},
viewModel: Module = module {  }) : KoinApplication =
    startKoin {
        modules(
            Modules.core,
            Modules.repositories,
            Modules.useCases,
            koinAppDeclaration,
            viewModel,
            platformModule
        )
    }


