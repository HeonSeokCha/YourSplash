package com.chs.yoursplash.presentation.bottom.home

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.presentation.LoadingState
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val pagingImageList: Flow<PagingData<Photo>>? = null,
    val loadingState: LoadingState = LoadingState.Initial,
    val isRefresh: Boolean = false
) {
    val isLoading: Boolean get() = loadingState is LoadingState.Loading
}
