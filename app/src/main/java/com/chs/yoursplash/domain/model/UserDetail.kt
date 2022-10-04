package com.chs.yoursplash.domain.model

data class UserDetail(
    val id: String,
    val userName: String,
    val name: String,
    val bio: String,
    val profileImage: UserProfileImage,
    val totalCollections: Int,
    val totalLikes: Int,
    val totalPhotos: Int,
    val tags: List<UnSplashTag>,
    val followersCount: Int
)
