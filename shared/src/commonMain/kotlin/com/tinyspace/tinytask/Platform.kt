package com.tinyspace.tinytask

expect class Platform() {
    val osName: String
    val osVersion: String

    val deviceModel: String
    val cpuType: String

    val screen: ScreenInfo?

    fun logSystemInfo()
}

expect class ScreenInfo() {
    val width: Int
    val height: Int
    val density: Int
}