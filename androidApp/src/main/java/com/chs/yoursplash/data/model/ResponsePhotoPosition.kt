package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePhotoPosition(
    @SerialName("latitude")
    val latitude: Float? = null,
    @SerialName("longitude")
    val longitude: Float? = null
)
