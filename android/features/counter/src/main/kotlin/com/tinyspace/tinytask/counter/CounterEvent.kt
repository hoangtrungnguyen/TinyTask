package com.tinyspace.tinytask.counter

import com.tinyspace.common.CommonEvent

sealed class CounterEvent : CommonEvent {
    object Finish : CounterEvent()
    object Start : CounterEvent()
    object Stop : CounterEvent()
    object Resume : CounterEvent()
    object Restart : CounterEvent()
}

