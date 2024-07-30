package com.chs.yoursplash.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.yoursplash.R
import com.chs.yoursplash.util.fromMainRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    navController: NavHostController,
    searchHistoryList: List<String>,
    onQueryChange: (String) -> Unit,
    onDeleteSearchHistory: (String) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.fromMainRoute()) {
        is MainScreens.HomeScreen, MainScreens.CollectionScreen -> {
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
                        navController.navigate(MainScreens.SearchScreen)
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(MainScreens.SettingScreen)
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

        is MainScreens.SettingScreen -> {
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

        is MainScreens.SearchScreen -> {
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

        null -> Unit
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

