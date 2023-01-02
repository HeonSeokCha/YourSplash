package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.Immutable
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.util.PhotoSaveState

@Immutable
data class PhotoDetailState(
    val isLoading: Boolean = false,
    val imageDetailInfo: PhotoDetail? = null,
    val imageRelatedList: List<Photo> = listOf(),
    val imageSaveState: PhotoSaveState = PhotoSaveState.NOT_DOWNLOAD,
    val wallpaperQuality: String = "full",
    val loadQuality: String = "regular",
    val isError: Boolean = false,
)

