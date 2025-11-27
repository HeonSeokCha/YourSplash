package com.chs.yoursplash.presentation.main

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    backStack: SnapshotStateList<MainScreens>,
    textFieldState: TextFieldState,
    searchHistoryList: List<String>,
    onQueryChange: (String) -> Unit,
    onDeleteSearchHistory: (String) -> Unit
) {
    when {
        backStack.last() == MainScreens.PhotoScreen
                || backStack.last() == MainScreens.CollectionScreen -> {

            TopAppBar(
                title = {
                    Text(
                        text = "Your Splash",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }, actions = {
                    IconButton(onClick = {
                        onQueryChange("")
                        backStack.add(MainScreens.SearchScreen)
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = {
                            backStack.add(MainScreens.SettingScreen)
                        }
                    ) {
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

        backStack.last() == MainScreens.SettingScreen -> {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
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

        else -> {
            SearchAppBar(
                textFieldState = textFieldState,
                onSearch = onQueryChange,
                searchHistoryList = searchHistoryList,
                onDeleteSearchHistory = onDeleteSearchHistory,
                onBack = { backStack.removeLastOrNull() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchAppBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchHistoryList: List<String>,
    onDeleteSearchHistory: (String) -> Unit,
    onBack: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        SearchBar(
            modifier = if (expanded) {
                Modifier
            } else {
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 12.dp
                    )
                    .animateContentSize(spring(stiffness = Spring.StiffnessHigh))
            },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search here...") },
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                if (!expanded) onBack()
                                expanded = false
                            }
                        ) {
                            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                        }
                    },
                    trailingIcon = {
                        if (!expanded || textFieldState.text.isEmpty()) null
                        else {
                            IconButton(onClick = {
                                if (textFieldState.text.isNotEmpty()) {
                                    textFieldState.clearText()
                                } else {
                                    expanded = false
                                }
                            }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            tonalElevation = 1.dp,
            windowInsets = SearchBarDefaults.windowInsets
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(searchHistoryList) { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 14.dp)
                            .combinedClickable(
                                onClick = {
                                    textFieldState.edit { replace(0, length, title) }
                                    expanded = false
                                    onSearch(title)
                                },
                                onLongClick = {
                                    textFieldState.edit { replace(0, length, title) }
                                    isShowDialog = true
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
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
                    title = { Text(text = textFieldState.text.toString()) },
                    text = { Text(text = "Are You Sure Delete Search History?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                isShowDialog = false
                                onDeleteSearchHistory(textFieldState.text.toString())
                                textFieldState.clearText()
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
}
