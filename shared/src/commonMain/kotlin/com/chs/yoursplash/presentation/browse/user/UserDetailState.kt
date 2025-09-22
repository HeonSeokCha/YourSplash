package com.chs.yoursplash.presentation.browse.user

import com.chs.yoursplash.domain.model.UserDetail

data class UserDetailState(
    val userDetailInfo: UserDetail? = null,
    val isUserDetailLoading: Boolean = true,
    val selectIdx: Int = 0,
    val tabList: List<String> = listOf("PHOTOS", "LIKES", "COLLECTIONS"),
    val isPhotoLoading: Boolean = false,
    val isLikeLoading: Boolean = false,
    val isCollectLoading: Boolean = false
)
