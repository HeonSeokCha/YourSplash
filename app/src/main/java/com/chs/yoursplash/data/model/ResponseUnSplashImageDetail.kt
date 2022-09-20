package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUnSplashImageDetail(
    @SerialName("id")
    val id: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("color")
    val color: String,
    @SerialName("urls")
    val urls: ResponseUnSplashImageUrls,
    @SerialName("description")
    val description: String? = null,
    @SerialName("user")
    val user: ResponseUnSplashUser,
    @SerialName("exif")
    val exif: ResponseUnSplashExif,
    @SerialName("location")
    val location: ResponseUnSplashLocation
)
