package com.chs.yoursplash.presentation.bottom.photo

sealed interface PhotoEffect {
    data class NavigatePhotoDetail(val id: String) : PhotoEffect
    data class NavigateUserDetail(val name: String) : PhotoEffect
    data class ShowToast(val message: String) : PhotoEffect
}