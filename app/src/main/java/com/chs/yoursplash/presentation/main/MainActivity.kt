package com.chs.yoursplash.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chs.yoursplash.R
import com.chs.yoursplash.presentation.main.collection.CollectionScreen
import com.chs.yoursplash.presentation.main.home.HomeScreen
import com.chs.yoursplash.presentation.search.SearchActivity
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
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
                            onNavigationIconClick = {
                                 scope.launch {
                                     scaffoldState.drawerState.open()
                                 }
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
                            HomeScreen()
                        }
                        composable(BottomNavScreen.CollectionScreen.route) {
                            CollectionScreen()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainTopBar(
    onNavigationIconClick: () -> Unit
) {
    val context = LocalContext.current

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
                context.startActivity(
                    Intent(context, SearchActivity::class.java)
                )
            }) {
                Icon(
                    imageVector = Icons.TwoTone.Search,
                    contentDescription = null
                )
            }
        }, backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    )
}


@Composable
fun BottomBar(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavScreen.HomeScreen,
        BottomNavScreen.CollectionScreen,
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
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