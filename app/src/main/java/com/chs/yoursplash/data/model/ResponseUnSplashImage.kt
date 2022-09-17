package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUnSplashImage(
    @SerialName("id")
    val id: String,
    @SerialName("color")
    val color: String,
    @SerialName("urls")
    val urls: ResponseUnSplashImageUrls,
    @SerialName("user")
    val user: ResponseUnSplashUser
)