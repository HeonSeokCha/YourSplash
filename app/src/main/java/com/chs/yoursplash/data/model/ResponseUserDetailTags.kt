package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserDetailTags(
    @SerialName("custom")
    val custom: List<ResponseUnSplashTag>
)