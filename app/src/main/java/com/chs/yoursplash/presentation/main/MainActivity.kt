package com.chs.yoursplash.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import com.chs.yoursplash.presentation.main.about.Screen
import com.chs.yoursplash.presentation.search.SearchBottomSheet
import com.chs.yoursplash.presentation.search.SearchScreen
import com.chs.yoursplash.presentation.setting.SettingScreen
import com.chs.yoursplash.util.SearchFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val bottomSheetScaffoldState =
//                rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            val navController = rememberNavController()
            var searchKeyword by remember { mutableStateOf("") }
            var searchFilter by remember { mutableStateOf(SearchFilter()) }
            val scope = rememberCoroutineScope()
            YourSplashTheme {
//                ModalBottomSheetLayout(
//                    sheetState = bottomSheetScaffoldState,
//                    sheetContent = {
//                        SearchBottomSheet(
//                            searchFilter = searchFilter,
//                            onClick = {
//                                searchFilter = searchFilter.copy(
//                                    orderBy = it.orderBy,
//                                    color = it.color,
//                                    orientation = it.orientation
//                                )
//                                scope.launch {
//                                    bottomSheetScaffoldState.hide()
//                                }
//                            }
//                        )
//                    }
//                ) {
                    Scaffold(
                        topBar = {
                            MainTopBar(
                                navController = navController,
                                searchClicked = {
                                    searchKeyword = it
                                }, onBackClicked = {
                                    searchKeyword = ""
                                    navController.navigateUp()
                                }
                            )
                        },
                        bottomBar = {
                            BottomBar(navController = navController)
                        },
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
                            composable(Screen.SearchScreen.route) {
                                SearchScreen(
                                    searchKeyWord = searchKeyword,
                                    searchFilter = searchFilter,
                                    modalClick = {
                                        scope.launch {
//                                            if (bottomSheetScaffoldState.isVisible) {
//                                                bottomSheetScaffoldState.hide()
//                                            } else {
//                                                bottomSheetScaffoldState.show()
//                                            }
                                        }
                                    }, onBack = {
                                        searchKeyword = ""
                                        searchFilter = SearchFilter()
                                    }
                                )

//                                BackHandler(enabled = bottomSheetScaffoldState.isVisible) {
//                                    scope.launch {
//                                        bottomSheetScaffoldState.hide()
//                                    }
//                                }
                            }
                            composable(Screen.SettingScreen.route) {
                                SettingScreen()
                            }
                        }
                    }
                }
            }
        }
    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopBar(
    navController: NavHostController,
    searchClicked: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        BottomNavScreen.HomeScreen.route, BottomNavScreen.CollectionScreen.route -> {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 20.sp
                    )
                }, actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.SearchScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.Search,
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(Screen.SettingScreen.route)
                    }) {
                        Icon(Icons.Filled.Settings, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Screen.SettingScreen.route -> {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }

        Screen.SearchScreen.route -> {
            SearchAppBar(
                searchClicked = { searchQuery ->
                    searchClicked(searchQuery)
                }, onBackClicked = onBackClicked
            )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SearchAppBar(
    searchClicked: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    var textState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = textState,
            onValueChange = {
                textState = it
            },
            placeholder = {
                Text(
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        onBackClicked()
                    }
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    searchClicked(textState)
                    keyboardController?.hide()
                }
            )
        )
    }
}

@Composable
private fun BottomBar(
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
        NavigationBar {
            val currentDestination = navBackStackEntry?.destination
            items.forEach { destination ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(0.4f),
                        unselectedTextColor = Color.White.copy(0.4f)
                    ),
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
                    label = { Text(stringResource(destination.label)) }
                )
            }
        }
    }
}