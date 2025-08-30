package com.chs.yoursplash.presentation.bottom.home

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val pagingImageList: Flow<PagingData<Photo>>? = null,
    val isLoading: Boolean = true,
    val isRefresh: Boolean = false
)
