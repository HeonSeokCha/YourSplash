package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ResponsePhotoCollection(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("tags")
    val tags: List<ResponseUnSplashTag>,
    @SerialName("user")
    val user: ResponseUser,
)
