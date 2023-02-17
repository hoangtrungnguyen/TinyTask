package com.tinyspace.shared.core

expect class SystemClock() {
    fun elapsedRealtimeNanos(): Long

    fun elapsedRealtime(): Long
}