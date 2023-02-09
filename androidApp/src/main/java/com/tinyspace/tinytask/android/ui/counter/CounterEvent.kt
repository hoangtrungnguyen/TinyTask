package com.tinyspace.tinytask.android.ui.counter

sealed class CounterEvent {
    object Finish: CounterEvent()
    object Start: CounterEvent()
    object Stop: CounterEvent()
    object Resume: CounterEvent()
    object Restart : CounterEvent()
}

