package com.chs.yoursplash.presentation.main

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
