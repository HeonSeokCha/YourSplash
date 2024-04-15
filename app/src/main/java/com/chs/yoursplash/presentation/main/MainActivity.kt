package com.chs.yoursplash.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.chs.yoursplash.presentation.search.SearchScreen
import com.chs.yoursplash.presentation.setting.SettingScreen
import com.chs.yoursplash.util.SearchFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var searchQuery: String by remember { mutableStateOf("") }
            val state by viewModel.state.collectAsStateWithLifecycle()

            YourSplashTheme {
                Scaffold(
                    topBar = {
                        MainTopBar(
                            navController = navController,
                            searchHistoryList = state.searchHistory,
                            onQueryChange = {
                                if (it.isNotEmpty()) {
                                    viewModel.insertSearchHistory(it)
                                }
                                searchQuery = it
                            },
                            onDeleteSearchHistory = {
                                viewModel.deleteSearchHistory(it)
                            }
                        )
                    },
                    bottomBar = {
                        BottomBar(navController = navController)
                    },
                ) {
                    MainNavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        searchQuery = searchQuery,
                        onBack = { searchQuery = "" }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopBar(
    navController: NavHostController,
    searchHistoryList: List<String>,
    onQueryChange: (String) -> Unit,
    onDeleteSearchHistory: (String) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        BottomNavScreen.HomeScreen.route, BottomNavScreen.CollectionScreen.route -> {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }, actions = {
                    IconButton(onClick = {
                        navController.navigate(MainScreens.SearchScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(MainScreens.SettingScreen.route)
                    }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        MainScreens.SettingScreen.route -> {
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

        MainScreens.SearchScreen.route -> {
            SearchAppBar(
                onSearch = {
                    onQueryChange(it)
                },
                searchHistoryList = searchHistoryList,
                onDeleteSearchHistory = {
                    onDeleteSearchHistory(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchAppBar(
    onSearch: (String) -> Unit,
    searchHistoryList: List<String>,
    onDeleteSearchHistory: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            isSearchActive = false
            onSearch(it)
        },
        active = isSearchActive,
        onActiveChange = { isSearchActive = it },
        placeholder = { Text("Search here...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = {
                if (text.isNotEmpty()) {
                    text = ""
                } else {
                    isSearchActive = false
                }
            }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null
                )
            }
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchHistoryList) { title ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 14.dp)
                    .combinedClickable(
                        onClick = {
                            text = title
                            isSearchActive = false
                            onSearch(title)
                        },
                        onLongClick = {
                            text = title
                            isShowDialog = true
                        }
                    )
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = null
                    )
                    Text(text = title)
                }
            }
        }

        if (isShowDialog) {
            AlertDialog(
                onDismissRequest = { isShowDialog = false },
                title = { Text(text = text) },
                text = { Text(text = "Are You Sure Delete Search History?") },
                confirmButton = {
                    Button(
                        onClick = {
                            isShowDialog = false
                            onDeleteSearchHistory(text)
                        }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            isShowDialog = false
                        }) {
                        Text("Cancel")
                    }
                }
            )
        }
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
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            val currentDestination = navBackStackEntry?.destination
            items.forEach { destination ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.White.copy(0.4f),
                        unselectedTextColor = Color.White.copy(0.4f),
                        indicatorColor = MaterialTheme.colorScheme.primary
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