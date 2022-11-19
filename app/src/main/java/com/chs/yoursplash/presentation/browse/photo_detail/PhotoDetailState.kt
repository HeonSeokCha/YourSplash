package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.Immutable
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail

@Immutable
data class PhotoDetailState(
    val isLoading: Boolean = false,
    val imageDetailInfo: PhotoDetail? = null,
    val imageRelatedList: List<Photo> = listOf(),
    val imageState:DownLoadState = DownLoadState.NOT_DOWNLOAD,
    val isError: Boolean = false,
)




enum class DownLoadState {
    NOT_DOWNLOAD, DOWNLOADING, DOWNLOADED
}

