package com.tinyspace.tinytask


expect class SystemClock() {
    fun elapsedRealtimeNanos(): Long
}