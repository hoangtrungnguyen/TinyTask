package com.tinyspace.tinytask

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform