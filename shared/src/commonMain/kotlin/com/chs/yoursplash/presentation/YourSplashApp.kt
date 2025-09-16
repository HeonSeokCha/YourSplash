package com.chs.yoursplash.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.bottom.BottomBar
import com.chs.yoursplash.presentation.main.MainNavHost
import com.chs.yoursplash.presentation.main.MainTopBar
import com.chs.yoursplash.presentation.main.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun YourSplashApp(onBrowseInfo: (BrowseInfo) -> Unit) {
    val navController: NavHostController = rememberNavController()
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val textFieldState = rememberTextFieldState()

    Scaffold(
        topBar = {
            MainTopBar(
                navController = navController,
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
            BottomBar(navController = navController)
        }
    ) {
        MainNavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            searchQuery = state.searchQuery,
            onBrowse = onBrowseInfo,
            onBack = {
                viewModel.updateSearchQuery("")
                textFieldState.clearText()
            }
        )
    }
}