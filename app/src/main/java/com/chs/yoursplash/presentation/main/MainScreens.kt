package com.chs.yoursplash.presentation.main

sealed class MainScreens(
    val route: String
) {
    data object SearchScreen : MainScreens("search_screen")
    data object SettingScreen : MainScreens("setting_screen")
}
