package com.chs.yoursplash.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chs.yoursplash.R
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.image_detail.ImageDetailScreen
import com.chs.yoursplash.presentation.main.collection.CollectionScreen
import com.chs.yoursplash.presentation.main.home.HomeScreen
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import com.chs.yoursplash.presentation.user.UserDetailScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            YourSplashTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        MainTopBar(
                            navController = navController,
                            onNavigationIconClick = {
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                        )
                    },
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "Setting",
                                    title = "Setting",
                                    icon = Icons.Default.Home
                                ),
                                MenuItem(
                                    id = "About",
                                    title = "About",
                                    icon = Icons.Default.Home
                                )
                            ),
                            onItemClick = {
                                when (it.id) {
                                    "Setting" -> { }
                                    "About" -> { }
                                }
                            }
                        )
                    }, bottomBar = {
                        BottomBar(navController = navController)
                    }
                ) {
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(it),
                        startDestination = BottomNavScreen.HomeScreen.route
                    ) {
                        composable(BottomNavScreen.HomeScreen.route) {
                            HomeScreen(navController)
                        }
                        composable(BottomNavScreen.CollectionScreen.route) {
                            CollectionScreen()
                        }
                        composable(
                            "${Screens.ImageDetailScreen.route}/{id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            ImageDetailScreen(
                                photoId = backStackEntry.arguments?.getString("id")!!,
                                navController = navController
                            )
                        }

                        composable(
                            "${Screens.UserDetailScreen.route}/{id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            UserDetailScreen(
                                userId = backStackEntry.arguments?.getString("id")!!,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainTopBar(
    navController: NavHostController,
    onNavigationIconClick: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    if (navBackStackEntry?.destination?.route == BottomNavScreen.HomeScreen.route
        || navBackStackEntry?.destination?.route == BottomNavScreen.CollectionScreen.route) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 20.sp
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    onNavigationIconClick()
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            }, actions = {
                IconButton(onClick = {
                }) {
                    Icon(
                        imageVector = Icons.TwoTone.Search,
                        contentDescription = null
                    )
                }
            }, backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        )
    } else {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }, backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentColor = Color.White
        )
    }
}


@Composable
fun BottomBar(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavScreen.HomeScreen,
        BottomNavScreen.CollectionScreen,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    if (navBackStackEntry?.destination?.route == BottomNavScreen.HomeScreen.route ||
        navBackStackEntry?.destination?.route == BottomNavScreen.CollectionScreen.route
    ) {
        BottomNavigation {
            val currentDestination = navBackStackEntry?.destination
            items.forEach { destination ->
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = stringResource(destination.label)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(destination.label),
                        )
                    }
                )
            }
        }
    }
}