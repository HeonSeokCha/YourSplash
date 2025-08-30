package com.chs.yoursplash.presentation.bottom.collection


sealed class CollectionEvent {
    data class BrowseCollectionDetail(val id: String) : CollectionEvent()
    data class BrowseUserDetail(val name: String) : CollectionEvent()
    data object OnRefresh : CollectionEvent()
}