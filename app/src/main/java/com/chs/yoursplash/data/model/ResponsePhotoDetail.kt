package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePhotoDetail(
    @SerialName("id")
    val id: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("color")
    val color: String,
    @SerialName("blur_hash")
    val blurHash: String,
    @SerialName("likes")
    val likes: Int,
    @SerialName("urls")
    val urls: ResponsePhotoUrls,
    @SerialName("description")
    val description: String? = null,
    @SerialName("user")
    val user: ResponseUser,
    @SerialName("exif")
    val exif: ResponseExif,
    @SerialName("location")
    val location: ResponsePhotoLocation,
    @SerialName("tags")
    val tags: List<ResponseUnSplashTag>,
    @SerialName("views")
    val views: Int,
    @SerialName("downloads")
    val downloads: Int
)
