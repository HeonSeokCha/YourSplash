package com.chs.yoursplash.presentation.search

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.annotation.concurrent.Immutable

data class SearchState(
    val searchType: String? = null,
    val searchPhotoList: Flow<PagingData<Photo>>? = null,
    val searchCollectionList: Flow<PagingData<UnSplashCollection>>? = null,
    val searchUserList: Flow<PagingData<User>>? = null,
    val loadQuality: String = "regular",
    val orderBy: String = "relevant",
    val color: String? = null,
    val orientation: String? = null
)
