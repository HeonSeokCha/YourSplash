package presentation.bottom

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import presentation.main.BottomNavigation

@Composable
fun BottomBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: BottomNavigation.HOME::class.qualifiedName.orEmpty()

    val currentRouteTrimmed by remember(currentRoute) {
        derivedStateOf { currentRoute.substringBefore("?") }
    }


    if (BottomNavigation.entries.any { it.route::class.qualifiedName == currentRoute }) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            BottomNavigation.entries.forEachIndexed { idx, navItem ->
                val isSelected by remember(currentRoute) {
                    derivedStateOf { currentRouteTrimmed == navItem.route::class.qualifiedName }
                }
                NavigationBarItem(
                    selected = isSelected,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(0.4f),
                        unselectedTextColor = Color.White.copy(0.4f),
                        indicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            navItem.icon,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = navItem.label) }
                )
            }
        }
    }
}
