package com.tinyspace.shared.core

expect class SystemClock constructor() {
    fun elapsedRealtimeNanos(): Long

    fun elapsedRealtime(): Long
}