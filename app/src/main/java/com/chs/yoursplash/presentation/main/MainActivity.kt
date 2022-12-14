package com.chs.yoursplash.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.chs.yoursplash.presentation.search.SearchScreen
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.SearchFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            val navController = rememberNavController()
            var searchKeyword by remember { mutableStateOf("") }
            var searchFilter by remember { mutableStateOf(SearchFilter()) }
            val scope = rememberCoroutineScope()
            YourSplashTheme {
                ModalBottomSheetLayout(
                    sheetState = bottomSheetScaffoldState,
                    sheetContent = {
                        SearchBottomSheet(searchFilter) {
                             searchFilter = searchFilter.copy(
                                 orderBy = it.orderBy,
                                 color = it.color,
                                 orientation = it.orientation
                             )
                            scope.launch {
                                bottomSheetScaffoldState.hide()
                            }
                        }
                }) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            MainTopBar(
                                navController = navController,
                                onNavigationIconClick = {
                                    scope.launch { scaffoldState.drawerState.open() }
                                }
                            ) {
                                searchKeyword = it
                            }
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
                                        "Setting" -> {}
                                        "About" -> {}
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
                            composable(Screen.SearchScreen.route) {
                                SearchScreen(
                                    searchKeyWord = searchKeyword,
                                    searchFilter = searchFilter,
                                    modalClick = {
                                        scope.launch {
                                            if (bottomSheetScaffoldState.isVisible) {
                                                bottomSheetScaffoldState.hide()
                                            } else {
                                                bottomSheetScaffoldState.show()
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun MainTopBar(
    navController: NavHostController,
    onNavigationIconClick: () -> Unit,
    searchClicked: (String) -> Unit
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
                    navController.navigate(Screen.SearchScreen.route)
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
        SearchAppBar(navController) { searchQuery ->
            searchClicked(searchQuery)
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchAppBar(
    navController: NavHostController,
    searchClicked: (String) -> Unit
) {
    var textState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
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
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigateUp()
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
                    label = { Text(stringResource(destination.label)) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchBottomSheet(
    searchFilter: SearchFilter,
    onClick: (SearchFilter) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectOrder by remember { mutableStateOf(searchFilter.orderBy) }
    var selectedColor by remember { mutableStateOf(searchFilter.color) }
    var selectOri by remember { mutableStateOf(searchFilter.orientation) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(start = 8.dp)
    ) {
        Text(text = "Sort By")
        TabRow(selectedTabIndex = Constants.SORT_BY_LIST.keys.indexOf(selectOrder)) {
            Constants.SORT_BY_LIST.keys.forEach { title ->
                Row(
                    modifier = Modifier
                        .clickable(onClick = { selectOrder = title })
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title)
                }
            }
        }

        Text(text = "Color")
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = selectedColor,
                onValueChange = { },
                label = { Text("Categories") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                Constants.SEARCH_COLOR_LIST.keys.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedColor = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption)
                    }
                }
            }
        }

        Text(text = "Orientation")
        TabRow(selectedTabIndex = Constants.SEARCH_ORI_LIST.values.indexOf(searchFilter.orientation)) {
            Constants.SEARCH_ORI_LIST.keys.forEach { title ->
                Row(
                    modifier = Modifier
                        .clickable(onClick = { selectOri = title })
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title)
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            onClick = {
                SearchFilter(
                    orderBy = Constants.SORT_BY_LIST[selectOrder] ?: selectOrder,
                    color = Constants.SEARCH_COLOR_LIST[selectedColor],
                    orientation = Constants.SEARCH_ORI_LIST[selectOri]
                )
                onClick(searchFilter)
            }
        ) {
            Text(text = "APPLY")
        }
    }
}