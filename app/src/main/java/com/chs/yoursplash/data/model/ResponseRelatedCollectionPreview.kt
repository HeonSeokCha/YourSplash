package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ResponseRelatedCollectionPreview(
    @SerialName("id")
    val id: String,
    @SerialName("urls")
    val urls: ResponsePhotoUrls
)
