package com.chs.yoursplash.presentation.bottom

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.chs.yoursplash.presentation.main.BottomNavigation
import com.chs.yoursplash.presentation.main.MainScreens

@Composable
fun BottomBar(
    backStack: SnapshotStateList<MainScreens>,
    onClick: (MainScreens) -> Unit
) {
    if (BottomNavigation.entries.any { it.route == backStack.last() }) {
        NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
            BottomNavigation.entries.forEach { navItem ->
                NavigationBarItem(
                    selected = backStack.last() == navItem.route,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(0.4f),
                        unselectedTextColor = Color.White.copy(0.4f),
                        indicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        when (navItem.route) {
                            MainScreens.PhotoScreen -> {
                                if (backStack.last() == MainScreens.PhotoScreen) return@NavigationBarItem
                                backStack.clear()
                                backStack.add(MainScreens.PhotoScreen)
                            }

                            MainScreens.CollectionScreen -> {
                                if (backStack.last() == MainScreens.CollectionScreen) return@NavigationBarItem
                                if (backStack.last() == MainScreens.FavoriteScreen) {
                                    backStack.removeLast()
                                    return@NavigationBarItem
                                }
                                backStack.add(MainScreens.CollectionScreen)
                            }

                            MainScreens.FavoriteScreen -> {
                                if (backStack.last() == MainScreens.FavoriteScreen) return@NavigationBarItem
                                if (backStack.any { it == MainScreens.CollectionScreen }) {
                                    backStack.add(MainScreens.CollectionScreen)
                                    backStack.add(MainScreens.FavoriteScreen)
                                    return@NavigationBarItem
                                }

                                backStack.add(MainScreens.FavoriteScreen)
                            }

                            else -> Unit
                        }
                    },
                    icon = { Icon(imageVector = navItem.icon, contentDescription = null) },
                    label = { Text(text = navItem.label) }
                )
            }
        }
    }
}
