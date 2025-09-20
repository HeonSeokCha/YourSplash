package com.chs.yoursplash.presentation.browse.photo_tag


sealed interface PhotoTagEffect {
    data class NavigatePhotoDetail(val id: String) : PhotoTagEffect
    data class NavigateUser(val name: String) : PhotoTagEffect
    data class ShowToast(val message: String) : PhotoTagEffect
    data object Close : PhotoTagEffect
}