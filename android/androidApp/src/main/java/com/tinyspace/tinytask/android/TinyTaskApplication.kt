package com.tinyspace.tinytask.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.tinyspace.shared.domain.GetRecentTaskUseCase
import com.tinyspace.taskform.TaskFormViewModel
import com.tinyspace.tinytask.counter.CounterViewModel
import com.tinyspace.tinytask.initKoin
import com.tinyspace.todolist.ToDoListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


class TinyTaskApplication: Application() {
    override fun onCreate() {
        Dispatchers.IO
        super.onCreate()
        initKoin(
            viewModel =
               module {
                   viewModel {
                       TaskFormViewModel(get())
                   }
                   viewModel { ToDoListViewModel(get<GetRecentTaskUseCase>()) }
                   viewModel { (taskId: String) ->
                       CounterViewModel(
                           taskId = taskId,
                           get()
                       )
                   }
               },
            koinAppDeclaration = module {
                single<Context> { this@TinyTaskApplication }
                single<SharedPreferences> {
                    get<Context>().getSharedPreferences("TinyTaskApp", MODE_PRIVATE)
                }
            },

        )
    }
}

