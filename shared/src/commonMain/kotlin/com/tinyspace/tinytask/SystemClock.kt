package com.tinyspace.tinytask


expect class SystemClock() {
    fun elapsedRealtimeNanos(): Long

    fun elapsedRealtime(): Long
}