package com.chs.yoursplash.presentation.image_detail

import androidx.compose.runtime.Immutable
import com.chs.yoursplash.domain.model.UnSplashImageDetail

@Immutable
data class ImageDetailState(
    val isLoading: Boolean = false,
    val imageDetailInfo: UnSplashImageDetail? = null,
    val isDownloading: Boolean = false,
    val isSavedFile: Boolean = false,
    val isError: Boolean = false,
)
