package com.chs.yoursplash.presentation.bottom.home

sealed interface HomeEffect {
    data class NavigatePhotoDetail(val id: String) : HomeEffect
    data class NavigateUserDetail(val name: String) : HomeEffect
    data class ShowToast(val message: String) : HomeEffect
}