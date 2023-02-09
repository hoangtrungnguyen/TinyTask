package com.tinyspace.tinytask.di

import com.tinyspace.tinytask.domain.SaveTaskUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


private val useCaseModule = module {
    factory { SaveTaskUseCase() }
}

private val sharedModules = listOf(useCaseModule)


fun initKoin(koinAppDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        koinAppDeclaration()
        modules(sharedModules)
    }


