package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.Immutable
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.util.PhotoSaveState

data class PhotoDetailState(
    val isLoading: Boolean = true,
    val imageDetailInfo: PhotoDetail? = null,
    val imageRelatedList: List<Photo> = listOf(),
    val imageSaveState: PhotoSaveState = PhotoSaveState.NOT_DOWNLOAD,
    val wallpaperQuality: String = "full",
    val loadQuality: String = "Regular",
    val downloadFileName: String? = null,
    val isError: Boolean = false,
)

