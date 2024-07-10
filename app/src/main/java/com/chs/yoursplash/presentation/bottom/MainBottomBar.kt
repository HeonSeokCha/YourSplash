package com.chs.yoursplash.presentation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.yoursplash.presentation.main.MainScreens
import com.chs.yoursplash.util.fromMainRoute

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val items = listOf(
        Triple(MainScreens.HomeScreen, Icons.Default.Home, "Home"),
        Triple(MainScreens.CollectionScreen, Icons.Default.Collections, "Collections")
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.fromMainRoute()

    if (currentDestination is MainScreens.HomeScreen ||
        currentDestination is MainScreens.CollectionScreen
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary
        ) {

            items.forEach { destination ->
                NavigationBarItem(
                    selected = items.any { it == currentDestination },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(0.4f),
                        unselectedTextColor = Color.White.copy(0.4f),
                        indicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        navController.navigate(destination.first) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            destination.second,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = destination.third) }
                )
            }
        }
    }
}
