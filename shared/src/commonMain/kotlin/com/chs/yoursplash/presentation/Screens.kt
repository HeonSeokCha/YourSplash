package com.chs.yoursplash.presentation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class BrowseScreens : NavKey {
    @Serializable
    data class PhotoDetailScreen(val photoId: String) : BrowseScreens()

    @Serializable
    data class UserDetailScreen(val userName: String) : BrowseScreens()

    @Serializable
    data class CollectionDetailScreen(val collectionId: String) : BrowseScreens()

    @Serializable
    data class PhotoTagResultScreen(val tagName: String) : BrowseScreens()

    @Serializable
    data class PhotoDetailViewScreen(val url: String) : BrowseScreens()
}