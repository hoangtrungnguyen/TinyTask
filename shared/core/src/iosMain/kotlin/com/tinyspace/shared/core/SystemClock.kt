package com.tinyspace.shared.core

actual class SystemClock {
    actual fun elapsedRealtimeNanos(): Long {
       return 1L
    }

    actual fun elapsedRealtime(): Long {
        return 1L
    }
}