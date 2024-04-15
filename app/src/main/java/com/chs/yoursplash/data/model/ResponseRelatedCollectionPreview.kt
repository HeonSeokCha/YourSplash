package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRelatedCollectionPreview(
    @SerialName("id")
    val id: String,
    @SerialName("urls")
    val urls: ResponsePhotoUrls,
    @SerialName("blur_hash")
    val blurHash: String? = null
)
