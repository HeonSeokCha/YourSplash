package com.chs.yoursplash.presentation.browse.image_detail

import androidx.compose.runtime.Immutable
import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.domain.model.UnSplashImageDetail

@Immutable
data class ImageDetailState(
    val isLoading: Boolean = false,
    val imageDetailInfo: UnSplashImageDetail? = null,
    val imageRelatedList: List<UnSplashImage>? = listOf(),
    val isDownloading: Boolean = false,
    val isSavedFile: Boolean = false,
    val isError: Boolean = false,
)
