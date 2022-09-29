package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUnSplashTag(
    @SerialName("type")
    val type: String,
    @SerialName("title")
    val title: String
)
