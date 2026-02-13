package com.chs.yoursplash.presentation.search

import com.chs.yoursplash.domain.model.SearchFilter

data class SearchState(
    val searchHistory: List<String> = emptyList(),

    val searchFilter: SearchFilter = SearchFilter(),
    val isPhotoLoading: Boolean = false,
    val isPhotoAppendLoading: Boolean = false,

    val photoErrorMessage: String? = null,
    val isCollectionLoading: Boolean = false,
    val isCollectAppendLoading: Boolean = false,

    val collectionErrorMessage: String? = null,
    val isUserLoading: Boolean = false,
    val isUserAppendLoading: Boolean = false,

    val userErrorMessage: String? = null,
    val tabList: List<String> = listOf("PHOTOS", "COLLECTIONS", "USERS"),
    val selectIdx: Int = 0,
    val showModal: Boolean = false,
    val expandColorFilter: Boolean = false
)
