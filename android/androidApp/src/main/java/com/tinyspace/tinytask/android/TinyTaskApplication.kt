package com.tinyspace.tinytask.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.tinyspace.android.stat.StatViewModel
import com.tinyspace.common.SHARE_PREF
import com.tinyspace.payment.CHPlayInterfaceImpl
import com.tinyspace.payment.PaymentBuilder
import com.tinyspace.payment.PaymentVM
import com.tinyspace.taskform.TaskFormViewModel
import com.tinyspace.taskhistory.TaskHistoryViewModel
import com.tinyspace.tinytask.counter.CounterViewModel
import com.tinyspace.tinytask.counter.sensor.CounterSensor
import com.tinyspace.tinytask.initKoin
import com.tinyspace.todolist.todoListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


class TinyTaskApplication: Application() {
    override fun onCreate() {
        Dispatchers.IO
        super.onCreate()
        initKoin(
            koinAppDeclaration = module {
                single<Context> { this@TinyTaskApplication }
                single<SharedPreferences> {
                    get<Context>().getSharedPreferences(SHARE_PREF, MODE_PRIVATE)
                }
                single<CounterSensor> {
                    CounterSensor(get())
                }
                single<PaymentBuilder> {
                    PaymentBuilder(get())
                }
                factory<CHPlayInterfaceImpl> {
                    CHPlayInterfaceImpl(get())
                }
            },
            viewModel =
            module {
                viewModel {
                    TaskFormViewModel(get(), get(), get())
                }
                includes(todoListViewModel)
                viewModel { (taskId: String) ->
                    CounterViewModel(taskId = taskId, get(), get(), get())
                }
                viewModel {
                       StatViewModel(get(), get(), get())
                   }
                   viewModel {
                       TaskHistoryViewModel(get())
                   }
                   viewModel {
                       PaymentVM(get(), get(), get(), get())
                   }
               },
            )
    }
}

