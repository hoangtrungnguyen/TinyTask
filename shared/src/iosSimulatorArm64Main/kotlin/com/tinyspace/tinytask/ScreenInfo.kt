package com.tinyspace.tinytask

actual class ScreenInfo actual constructor() {
    actual val width: Int
        get() = TODO("Not yet implemented")
    actual val height: Int
        get() = TODO("Not yet implemented")
    actual val density: Int
        get() = TODO("Not yet implemented")
}

actual class Platform actual constructor() {
    actual val osName: String
        get() = TODO("Not yet implemented")
    actual val osVersion: String
        get() = TODO("Not yet implemented")
    actual val deviceModel: String
        get() = TODO("Not yet implemented")
    actual val cpuType: String
        get() = TODO("Not yet implemented")
    actual val screen: ScreenInfo?
        get() = TODO("Not yet implemented")

    actual fun logSystemInfo() {
    }

}