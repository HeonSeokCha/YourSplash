package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRelatedPhoto(
    @SerialName("total")
    val total: Int,
    @SerialName("results")
    val results: List<ResponsePhoto>
)