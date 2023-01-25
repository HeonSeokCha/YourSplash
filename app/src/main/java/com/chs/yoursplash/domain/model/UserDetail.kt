package com.chs.yoursplash.domain.model

data class UserDetail(
    val id: String,
    val userName: String,
    val name: String,
    val bio: String?,
    val location: String?,
    val profileImage: UserProfileImage,
    val totalCollections: Int = 0,
    val totalLikes: Int = 0,
    val totalPhotos: Int = 0,
    val tags: List<UnSplashTag>,
    val followersCount: Int
)
