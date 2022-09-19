package com.chs.yoursplash.domain.model

data class UnSplashImage(
    val id: String,
    val color: String,
    val width: Int,
    val height: Int,
    val urls: UnSplashImageUrls,
    val user: UnSplashUser
)