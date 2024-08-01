package com.chs.yoursplash.presentation.browse.user

import androidx.paging.PagingData
import domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

data class UserDetailState(
    val userDetailInfo: UserDetail? = null,
    val userDetailPhotoList: Flow<PagingData<Photo>>? = null,
    val userDetailLikeList: Flow<PagingData<Photo>>? = null,
    val userDetailCollection: Flow<PagingData<UnSplashCollection>>? = null,
    val userTabLabList: List<String> = listOf("PHOTOS", "LIKES", "COLLECT"),
    val loadQuality: String = "Regular",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
