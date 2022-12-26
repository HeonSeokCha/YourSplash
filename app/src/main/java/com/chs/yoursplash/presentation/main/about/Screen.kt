package com.chs.yoursplash.presentation.main.about

sealed class Screen(
    val route: String
) {
    object SearchScreen : Screen("search_screen")
    object SettingScreen : Screen("setting_screen")
}