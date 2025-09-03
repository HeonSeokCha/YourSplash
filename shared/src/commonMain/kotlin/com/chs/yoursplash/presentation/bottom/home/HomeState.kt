package com.chs.yoursplash.presentation.bottom.home

import com.chs.yoursplash.presentation.LoadingState

data class HomeState(
    val loadingState: LoadingState = LoadingState.Initial,
    val isRefresh: Boolean = false
) {
    val isLoading: Boolean get() = loadingState is LoadingState.Loading
}
