package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ResponseSearchPhotos(
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("results")
    val result: List<ResponsePhoto>
)
