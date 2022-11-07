package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUser(
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val userName: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val photoProfile: ResponseUserProfileImage,
    @SerialName("photos")
    val photos: List<ResponseUserPhotos> = listOf()
)