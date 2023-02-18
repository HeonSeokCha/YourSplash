package com.chs.yoursplash.presentation.main

sealed class MainScreens(
    val route: String
) {
    object SearchScreen : MainScreens("search_screen")
    object SettingScreen : MainScreens("setting_screen")
}
