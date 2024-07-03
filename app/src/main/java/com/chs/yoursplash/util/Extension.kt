package com.chs.yoursplash.util

import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import com.chs.yoursplash.presentation.main.MainScreens


val String.color
    get() =  Color(parseColor(this))


fun NavBackStackEntry?.fromRoute(): MainScreens? {
    return this?.destination?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            return when (it) {
                MainScreens.SearchScreen::class.simpleName -> MainScreens.SearchScreen
                MainScreens.HomeScreen::class.simpleName -> MainScreens.HomeScreen
                MainScreens.CollectionScreen::class.simpleName -> MainScreens.HomeScreen
                MainScreens.SettingScreen::class.simpleName -> MainScreens.SettingScreen
                else -> null
            }
        }
}