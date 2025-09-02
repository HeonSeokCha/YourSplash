package com.chs.yoursplash.presentation

sealed interface LoadingState {
    object Initial : LoadingState
    object Loading : LoadingState
    object Success : LoadingState
}
