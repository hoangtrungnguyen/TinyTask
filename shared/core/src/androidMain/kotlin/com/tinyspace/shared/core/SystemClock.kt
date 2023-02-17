package com.tinyspace.shared.core

actual class SystemClock actual constructor() {
    actual fun elapsedRealtimeNanos(): Long = android.os.SystemClock.elapsedRealtimeNanos()
    actual fun elapsedRealtime(): Long = android.os.SystemClock.elapsedRealtime()
}