package com.chs.yoursplash.presentation.browse

import kotlinx.serialization.Serializable

@Serializable
sealed interface BrowseScreens {
    @Serializable
    data class PhotoDetailScreen(val photoId: String) : BrowseScreens

    @Serializable
    data class UserDetailScreen(val userName: String) : BrowseScreens

    @Serializable
    data class CollectionDetailScreen(val collectionId: String) : BrowseScreens

    @Serializable
    data class PhotoTagResultScreen(val tagName: String) : BrowseScreens

    @Serializable
    data class PhotoDetailViewScreen(val url: String) : BrowseScreens
}