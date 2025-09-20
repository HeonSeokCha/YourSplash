package com.chs.yoursplash.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    data class PhotoDetailScreen(val photoId: String) : Screens()

    @Serializable
    data class UserDetailScreen(val userName: String) : Screens()

    @Serializable
    data class CollectionDetailScreen(val collectionId: String) : Screens()

    @Serializable
    data class PhotoTagResultScreen(val tagName: String) : Screens()
}