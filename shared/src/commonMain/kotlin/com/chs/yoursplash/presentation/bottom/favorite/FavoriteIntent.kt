package com.chs.yoursplash.presentation.bottom.favorite

sealed interface FavoriteIntent {
    data class ClickPhoto(val id: String) : FavoriteIntent
}