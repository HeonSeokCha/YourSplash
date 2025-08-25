package com.chs.yoursplash.presentation.search

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchFilter: SearchFilter = SearchFilter(),
    val searchPhotoList: Flow<PagingData<Photo>>? = null,
    val searchCollectionList: Flow<PagingData<UnSplashCollection>>? = null,
    val searchUserList: Flow<PagingData<User>>? = null
)
