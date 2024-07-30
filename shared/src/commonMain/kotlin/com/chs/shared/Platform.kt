package com.chs.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform