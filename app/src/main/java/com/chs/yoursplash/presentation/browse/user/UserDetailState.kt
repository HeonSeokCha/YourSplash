package com.chs.yoursplash.presentation.browse.user

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

data class UserDetailState(
    val userDetailInfo: UserDetail? = null,
    val userDetailPhotoList: Flow<PagingData<Photo>>? = null,
    val userDetailLikeList: Flow<PagingData<Photo>>? = null,
    val userDetailCollection: Flow<PagingData<UnSplashCollection>>? = null,
    val userTabLabList: MutableList<String> = mutableListOf(),
    val loadQuality: String = "regular",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
