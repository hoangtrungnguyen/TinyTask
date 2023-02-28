package com.tinyspace.todolist

import com.tinyspace.shared.domain.GetRecentTaskUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val todoListViewModel = module {
    viewModel { ToDoListViewModel(get<GetRecentTaskUseCase>()) }
}