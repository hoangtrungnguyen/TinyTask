package com.tinyspace.tinytask

actual class SystemClock {
    actual  fun elapsedRealtimeNanos(): Long =  android.os.SystemClock.elapsedRealtimeNanos()
}