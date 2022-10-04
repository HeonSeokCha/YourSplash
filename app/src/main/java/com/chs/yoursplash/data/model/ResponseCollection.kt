package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCollection(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("tags")
    val tags: List<ResponseUnSplashTag>,
    @SerialName("user")
    val user: ResponseUser,
    @SerialName("preview_photos")
    val previewPhotos: List<ResponseRelatedCollectionPreview>
)
