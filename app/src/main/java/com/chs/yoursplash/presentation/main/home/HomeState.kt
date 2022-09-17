package com.chs.yoursplash.presentation.main.home

import com.chs.yoursplash.domain.model.UnSplashImage

data class HomeState(
    val imageList: List<UnSplashImage> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
