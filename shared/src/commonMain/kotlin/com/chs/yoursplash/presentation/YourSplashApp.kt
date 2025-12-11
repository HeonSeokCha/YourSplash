package com.chs.yoursplash.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.bottom.BottomBar
import com.chs.yoursplash.presentation.main.MainNavDisplay
import com.chs.yoursplash.presentation.main.MainScreens
import com.chs.yoursplash.presentation.main.MainTopBar
import com.chs.yoursplash.presentation.main.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun YourSplashApp(onBrowseInfo: (BrowseInfo) -> Unit) {
    val backStack: SnapshotStateList<MainScreens> = remember { mutableStateListOf(MainScreens.PhotoScreen) }
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val textFieldState = rememberTextFieldState()

    Scaffold(
        topBar = {
            MainTopBar(
                backStack = backStack,
                textFieldState = textFieldState,
                searchHistoryList = state.searchHistory,
                onQueryChange = {
                    if (it.isNotEmpty()) {
                        viewModel.insertSearchHistory(it)
                    }
                    textFieldState.edit { replace(0, length, it) }
                    viewModel.updateSearchQuery(it)
                },
                onDeleteSearchHistory = {
                    viewModel.deleteSearchHistory(it)
                }
            )
        },
        bottomBar = {
            BottomBar(backStack)
        }
    ) {
        MainNavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            backStack = backStack,
            searchQuery = state.searchQuery,
            onBrowse = onBrowseInfo
        )
    }
}