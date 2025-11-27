package com.chs.yoursplash.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.Photo
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainScreens {
    @Serializable
    data object PhotoScreen : MainScreens

    @Serializable
    data object CollectionScreen : MainScreens

    @Serializable
    data object SearchScreen : MainScreens

    @Serializable
    data object SettingScreen : MainScreens
}

enum class BottomNavigation(
    val label: String,
    val icon: ImageVector,
    val route: MainScreens
) {
    Photo("Photo", Icons.Filled.Photo, MainScreens.PhotoScreen),
    Collection("Collection", Icons.Filled.PermMedia, MainScreens.CollectionScreen),
}
