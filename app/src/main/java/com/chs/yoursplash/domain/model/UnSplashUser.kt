package com.chs.yoursplash.domain.model

data class UnSplashUser(
    val id: String,
    val userName: String,
    val photoProfile: UnsplashUserProfileImage,
)