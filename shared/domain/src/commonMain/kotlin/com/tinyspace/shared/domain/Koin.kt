package com.tinyspace.shared.domain

import org.koin.dsl.module

class Koin {
    companion object {
        val useCases = module {
            factory { GetRecentTaskUseCase(get()) }
            factory { SaveTaskUseCase(get(), get(), get()) }
            factory { UpdateTaskUseCase(get()) }
            factory { GetTaskUseCase(get()) }
            factory { CountTaskUseCase(get()) }
            factory { GetTotalDurationTaskUseCase(get()) }
            factory { GetTaskPaginationUseCase(get()) }
            factory { GetTodayHighlightUseCase(get()) }
            factory { VerifySuccessPurchaseUseCase(get()) }
            factory { GetTagUseCase(get()) }
            factory { GetWeekDayTimeSpent(get()) }
        }
    }
}