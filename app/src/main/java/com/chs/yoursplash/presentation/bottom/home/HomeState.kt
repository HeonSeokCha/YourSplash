package com.chs.yoursplash.presentation.bottom.home

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import kotlinx.coroutines.flow.Flow

@Immutable
data class HomeState(
    val pagingImageList: Flow<PagingData<Photo>>? = null,
    val loadQuality: String = "regular",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
