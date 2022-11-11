package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePhoto(
    @SerialName("id")
    val id: String,
    @SerialName("color")
    val color: String,
    @SerialName("blur_hash")
    val blurHash: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("urls")
    val urls: ResponsePhotoUrls,
    @SerialName("user")
    val user: ResponseUser
)