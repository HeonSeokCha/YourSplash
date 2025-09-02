package com.chs.yoursplash.presentation

sealed interface ErrorState {
    object Initial : ErrorState
    data class Error(val message: String) : ErrorState
}