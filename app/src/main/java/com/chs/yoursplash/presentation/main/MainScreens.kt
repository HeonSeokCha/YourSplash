package com.chs.yoursplash.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class MainScreens {
    @Serializable
    data object HomeScreen : MainScreens()
    @Serializable
    data object CollectionScreen : MainScreens()
    @Serializable
    data object SearchScreen : MainScreens()
    @Serializable
    data object SettingScreen : MainScreens()
}

enum class BottomNavigation(
    val label: String,
    val icon: ImageVector,
    val route: MainScreens
){
    HOME("Home", Icons.Filled.Home, MainScreens.HomeScreen),
    SEARCH("Collection", Icons.Filled.Collections, MainScreens.CollectionScreen),
}
