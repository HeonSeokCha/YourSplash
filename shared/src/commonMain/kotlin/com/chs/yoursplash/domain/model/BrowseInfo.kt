package com.chs.yoursplash.domain.model

sealed class BrowseInfo {
    data class Photo(val id: String) : BrowseInfo()
    data class Collection(val id: String) : BrowseInfo()
    data class User(val name: String) : BrowseInfo()
}