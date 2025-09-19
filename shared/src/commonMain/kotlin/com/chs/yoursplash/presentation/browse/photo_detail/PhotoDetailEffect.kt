package com.chs.yoursplash.presentation.browse.photo_detail

sealed interface PhotoDetailEffect {
    data class NavigatePhotoDetail(val id: String) : PhotoDetailEffect
    data class NavigatePhotoTag(val tag: String) : PhotoDetailEffect
    data class NavigateUserDetail(val name: String) : PhotoDetailEffect
    data class ShowToast(val message: String) : PhotoDetailEffect
    data object Close : PhotoDetailEffect
}