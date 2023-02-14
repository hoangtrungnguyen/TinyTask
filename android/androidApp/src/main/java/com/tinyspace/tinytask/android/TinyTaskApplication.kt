package com.tinyspace.tinytask.android

import android.app.Application
import com.tinyspace.taskform.TaskFormViewModel
import com.tinyspace.tinytask.di.initKoin
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


class TinyTaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            viewModel =
               module {
                   viewModel { TaskFormViewModel(get()) }
               }

        )
    }
}

