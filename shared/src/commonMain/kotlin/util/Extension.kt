package util

import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import com.chs.yoursplash.presentation.Screens
import presentation.main.MainScreens


val String.color
    get() = Color(parseColor(this))


fun NavBackStackEntry?.fromMainRoute(): MainScreens? {
    return this?.destination?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            return when(it) {
                MainScreens.SearchScreen::class.simpleName -> MainScreens.SearchScreen
                MainScreens.HomeScreen::class.simpleName -> MainScreens.HomeScreen
                MainScreens.CollectionScreen::class.simpleName -> MainScreens.HomeScreen
                MainScreens.SettingScreen::class.simpleName -> MainScreens.SettingScreen
                else -> null
            }
        }
}

fun NavBackStackEntry?.fromScreenRoute(): Screens? {
    return this?.destination?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            return when(it) {
                Screens.PhotoTagResultScreen::class.simpleName -> Screens.PhotoTagResultScreen("")
                Screens.UserDetailScreen::class.simpleName -> Screens.UserDetailScreen("")
                Screens.ImageDetailScreen::class.simpleName -> Screens.ImageDetailScreen("")
                Screens.CollectionDetailScreen::class.simpleName -> Screens.CollectionDetailScreen("")
                else -> null
            }
        }
}
