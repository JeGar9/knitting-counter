package com.example.knitcounter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform