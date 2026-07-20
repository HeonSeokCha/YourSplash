package com.chs.yoursplash.presentation.bottom.favorite

sealed interface FavoriteEffect {
    data class NavigatePhotoDetail(val id: String) : FavoriteEffect
}