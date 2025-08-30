package com.chs.yoursplash.presentation.bottom.home

sealed class HomeEvent {
    data class BrowsePhotoDetail(val id: String) : HomeEvent()
    data class BrowseUserDetail(val name: String) : HomeEvent()
    data object OnRefresh : HomeEvent()
}