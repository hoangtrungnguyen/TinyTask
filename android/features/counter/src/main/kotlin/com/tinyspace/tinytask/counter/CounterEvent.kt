package com.tinyspace.tinytask.counter

import com.tinyspace.common.Event

sealed class CounterEvent : Event {
    object Finish : CounterEvent()
    object Start : CounterEvent()
    object Stop : CounterEvent()
    object Resume : CounterEvent()
    object Restart : CounterEvent()

    object Cancel : CounterEvent()
}

