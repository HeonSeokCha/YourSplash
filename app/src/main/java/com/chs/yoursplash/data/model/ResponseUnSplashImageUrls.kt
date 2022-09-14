package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUnSplashImageUrls(
    @SerialName("raw")
    val raw: String,
    @SerialName("full")
    val full: String,
    @SerialName("small")
    val small: String,
    @SerialName("thumb")
    val thumb: String,
    @SerialName("small_s3")
    val small_s3: String
)
