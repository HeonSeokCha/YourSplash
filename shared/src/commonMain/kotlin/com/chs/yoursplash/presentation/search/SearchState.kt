package com.chs.yoursplash.presentation.search

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val query: String = "",
    val searchFilter: SearchFilter = SearchFilter(),
    val photoLoadingState: Boolean = false,
    val searchPhotoList: Flow<PagingData<Photo>>? = null,
    val photoErrorMessage: String? = null,
    val collectionLoadingState: Boolean = false,
    val searchCollectionList: Flow<PagingData<UnSplashCollection>>? = null,
    val collectionErrorMessage: String? = null,
    val userLoadingState: Boolean = false,
    val searchUserList: Flow<PagingData<User>>? = null,
    val userErrorMessage: String? = null,
    val tabList: List<String> = listOf("PHOTOS", "COLLECTIONS", "USERS"),
    val selectIdx: Int = 0,
    val showModal: Boolean = false,
    val expandColorFilter: Boolean = false
)
