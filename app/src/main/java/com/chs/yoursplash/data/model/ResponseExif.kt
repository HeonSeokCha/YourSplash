package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseExif(
    @SerialName("make")
    val make: String?,
    @SerialName("model")
    val model: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("exposure_time")
    val exposureTime: String?,
    @SerialName("aperture")
    val aperture: String?,
    @SerialName("focal_length")
    val focalLength: String?,
    @SerialName("iso")
    val iso: Int?
)
