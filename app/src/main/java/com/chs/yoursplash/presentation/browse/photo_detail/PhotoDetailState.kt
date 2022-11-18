package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.Immutable
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail

@Immutable
data class PhotoDetailState(
    val isLoading: Boolean = false,
    val imageDetailInfo: PhotoDetail? = null,
    val imageRelatedList: List<Photo> = listOf(),
    val isDownloading: Boolean = false,
    val isSavedFile: Boolean = false,
    val isError: Boolean = false,
)
