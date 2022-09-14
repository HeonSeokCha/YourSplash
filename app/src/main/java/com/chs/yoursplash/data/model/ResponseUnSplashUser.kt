package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUnSplashUser(
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val userName: String,
    @SerialName("profile_image")
    val photoProfile: ResponseUnsplashUserProfileImage,
)