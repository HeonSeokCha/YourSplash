package com.chs.yoursplash.domain.model

data class UnSplashImageDetail(
    val id: String,
    val width: Int,
    val height: Int,
    val color: String,
    val description: String,
    val user: UnSplashUser,
    val exif: UnSplashExif,
    val location: UnSplashLocation
)
