package com.tinyspace.tinytask.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


private val useCaseModules = module {
    factory {

    }
}

private val sharedModules = listOf(useCaseModules)


fun initKoin(koinAppDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        koinAppDeclaration()
        modules(sharedModules)
    }


