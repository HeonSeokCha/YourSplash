package com.chs.yoursplash.domain.model

data class User(
    val id: String,
    val userName: String,
    val name: String,
    val photoProfile: UserProfileImage,
)