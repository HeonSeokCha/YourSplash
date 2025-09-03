package com.chs.yoursplash.presentation.bottom.collection

import com.chs.yoursplash.presentation.LoadingState

data class CollectionState(
    val loadingState: LoadingState = LoadingState.Initial,
    val isRefresh: Boolean = false
) {
    val isLoading: Boolean get() = loadingState is LoadingState.Loading
}
