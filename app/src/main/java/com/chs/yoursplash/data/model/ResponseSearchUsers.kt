package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ResponseSearchUsers(
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("results")
    val result: List<ResponseUser>
)
