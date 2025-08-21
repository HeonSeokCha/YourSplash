package com.chs.yoursplash.presentation.bottom.home

sealed class HomEvent {
    data class BrowsePhotoDetail(val id: String) : HomEvent()
    data class BrowseUserDetail(val name: String) : HomEvent()
}