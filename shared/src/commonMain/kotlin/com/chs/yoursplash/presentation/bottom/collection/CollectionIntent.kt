package com.chs.yoursplash.presentation.bottom.collection

sealed interface CollectionIntent {
    object Loading : CollectionIntent
    object LoadComplete : CollectionIntent
    object RefreshData : CollectionIntent
    data class OnError(val message: String?) : CollectionIntent
    data class ClickCollection(val id: String) : CollectionIntent
    data class ClickUser(val name: String) : CollectionIntent
}