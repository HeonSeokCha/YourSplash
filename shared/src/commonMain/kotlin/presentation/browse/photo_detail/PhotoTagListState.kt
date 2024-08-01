package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.paging.PagingData
import domain.model.Photo
import kotlinx.coroutines.flow.Flow

data class PhotoTagListState(
    val tagSearchResultList: Flow<PagingData<Photo>>? = null,
    val loadQuality: String = "Regular",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
