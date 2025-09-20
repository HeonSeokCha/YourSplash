package com.chs.yoursplash.presentation.browse.photo_tag


sealed interface PhotoTagIntent {
    object Loading : PhotoTagIntent
    object LoadComplete : PhotoTagIntent
    object RefreshData : PhotoTagIntent
    data class ClickPhoto(val id: String) : PhotoTagIntent
    data class ClickUser(val name: String) : PhotoTagIntent
    data class OnError(val error: String) : PhotoTagIntent
    data object ClickClose : PhotoTagIntent
}