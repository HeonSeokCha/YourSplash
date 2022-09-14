package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUnsplashUserProfileImage(
    @SerialName("small")
    val small: String,
    @SerialName("medium")
    val medium: String,
    @SerialName("large")
    val large: String
)
