package com.chs.yoursplash.presentation.main.home

import androidx.compose.runtime.Immutable
import com.chs.yoursplash.domain.model.UnSplashImage

@Immutable
data class HomeState(
    val imageList: List<UnSplashImage> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
