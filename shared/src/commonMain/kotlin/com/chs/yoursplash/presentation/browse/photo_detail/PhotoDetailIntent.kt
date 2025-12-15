package com.chs.yoursplash.presentation.browse.photo_detail

sealed interface PhotoDetailIntent {
    data class ClickPhotoDetail(val url: String) : PhotoDetailIntent
    data class ClickPhoto(val id: String) : PhotoDetailIntent
    data class ClickTag(val name: String) : PhotoDetailIntent
    data class ClickUser(val name: String) : PhotoDetailIntent
    data class ClickDownload(val url: String) : PhotoDetailIntent
    data class ClickOpenBrowser(val id: String) : PhotoDetailIntent
    data object ClickAlreadyDownload : PhotoDetailIntent
    data object ClickDismiss : PhotoDetailIntent
    data object ClickClose : PhotoDetailIntent
}