package com.chs.yoursplash.presentation.bottom.home

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import kotlinx.coroutines.flow.Flow

data class HomeEvent(
    val pagingImageList: Flow<PagingData<Photo>>? = null,
    val loadQuality: String = "Regular",
    val isLoading: Boolean = true,
)
