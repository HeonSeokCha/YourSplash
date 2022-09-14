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
    @SerialName("description")
    val description: String,
    @SerialName("user")
    val user: ResponseUnSplashUser,
    @SerialName("exif")
    val exif: ResponseUnSplashExif,
    @SerialName("location")
    val location: ResponseUnSplashLocation
)
