package com.chs.yoursplash.presentation.browse.photo_detail

import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail

data class PhotoDetailState(
    val isLoading: Boolean = true,
    val imageDetailInfo: PhotoDetail? = null,
    val imageRelatedList: List<Photo> = listOf(),
    val wallpaperQuality: String = "full",
    val loadQuality: String = "Regular",
    val downloadFileName: String? = null,
    val isError: Boolean = false,
    val errorMessage: String? = null,
)

