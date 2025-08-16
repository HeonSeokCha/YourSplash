package com.chs.yoursplash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUserDetail(
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val userName: String,
    @SerialName("name")
    val name: String,
    @SerialName("bio")
    val bio: String?,
    @SerialName("location")
    val location: String?,
    @SerialName("profile_image")
    val profileImage: ResponseUserProfileImage,
    @SerialName("total_collections")
    val totalCollection: Int,
    @SerialName("total_likes")
    val totalLikes: Int,
    @SerialName("total_photos")
    val totalPhotos: Int
)
