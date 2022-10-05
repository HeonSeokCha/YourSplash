package com.chs.yoursplash.presentation.browse.user

import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.UserDetail

data class UserDetailState(
    val userDetailInfo: UserDetail? = null,
    val userDetailPhotoList: List<Photo> = listOf(),
    val userDetailLikeList: List<Photo> = listOf(),
    val userDetailCollection: List<UnSplashCollection> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
