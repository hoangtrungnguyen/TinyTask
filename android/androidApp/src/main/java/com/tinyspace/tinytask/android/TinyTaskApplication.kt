package com.tinyspace.tinytask.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.tinyspace.taskform.TaskFormViewModel
import com.tinyspace.tinytask.initKoin
import com.tinyspace.todolist.ToDoListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
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
                   viewModelOf(::ToDoListViewModel)
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

