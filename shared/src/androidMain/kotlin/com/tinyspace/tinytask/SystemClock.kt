package com.tinyspace.tinytask

actual class SystemClock {
    actual  fun elapsedRealtimeNanos(): Long =  android.os.SystemClock.elapsedRealtimeNanos()
    actual  fun elapsedRealtime(): Long =  android.os.SystemClock.elapsedRealtime()
}