package com.chs.yoursplash.presentation.browse.photo_detail

import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail

data class PhotoDetailState(
    val isDetailLoading: Boolean = false,
    val isRelatedLoading: Boolean = false,
    val isFileDownLoading: Boolean = false,
    val isFileDownloaded: Boolean = false,
    val imageDetailInfo: PhotoDetail? = null,
    val imageRelatedList: List<Photo> = listOf(),
    val loadQualityValue: LoadQuality = LoadQuality.Regular,
    val downLoadQualityValue: LoadQuality = LoadQuality.Regular,
    val wallpaperQualityValue: LoadQuality = LoadQuality.Regular,
)

