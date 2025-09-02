package com.chs.yoursplash.presentation.bottom.home

sealed interface HomeIntent {
    object Loading : HomeIntent
    object LoadComplete : HomeIntent
    object RefreshData : HomeIntent
    data class OnError(val message: String?) : HomeIntent
    data class ClickPhoto(val id: String) : HomeIntent
    data class ClickUser(val name: String) : HomeIntent
}