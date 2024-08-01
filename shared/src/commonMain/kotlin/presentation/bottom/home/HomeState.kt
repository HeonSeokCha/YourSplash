package com.chs.yoursplash.presentation.bottom.home

import androidx.paging.PagingData
import domain.model.Photo
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val pagingImageList: Flow<PagingData<Photo>>? = null,
    val loadQuality: String = "Regular",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
