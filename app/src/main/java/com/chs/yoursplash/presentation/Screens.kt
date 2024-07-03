package com.chs.yoursplash.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {
    @Serializable
    data class ImageDetailScreen(val id: String) : Screens()

    @Serializable
    data class UserDetailScreen(val usageName: String) : Screens()

    @Serializable
    data class CollectionDetailScreen(val id: String) : Screens()

    @Serializable
    data class PhotoTagResultScreen(val tageName: String) : Screens()
}