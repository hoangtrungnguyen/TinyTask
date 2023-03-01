package com.tinyspace.tinytask

//import com.tinyspace.domain.
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
    }
