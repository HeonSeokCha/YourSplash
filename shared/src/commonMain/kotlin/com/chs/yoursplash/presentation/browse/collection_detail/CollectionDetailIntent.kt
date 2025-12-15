package com.chs.yoursplash.presentation.browse.collection_detail

sealed interface CollectionDetailIntent {
    object Loading : CollectionDetailIntent
    object LoadComplete : CollectionDetailIntent
    data class OnError(val message: String?) : CollectionDetailIntent
    data class ClickPhoto(val id: String) : CollectionDetailIntent
    data class ClickUser(val name: String) : CollectionDetailIntent
    data class ClickOpenBrowser(val id: String) : CollectionDetailIntent
    data object ClickClose : CollectionDetailIntent
}