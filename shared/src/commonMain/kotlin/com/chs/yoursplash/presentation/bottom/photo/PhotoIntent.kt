package com.chs.yoursplash.presentation.bottom.photo

sealed interface PhotoIntent {
    object Loading : PhotoIntent
    object LoadComplete : PhotoIntent
    object RefreshData : PhotoIntent
    data class OnError(val message: String?) : PhotoIntent
    data class ClickPhoto(val id: String) : PhotoIntent
    data class ClickUser(val name: String) : PhotoIntent
}