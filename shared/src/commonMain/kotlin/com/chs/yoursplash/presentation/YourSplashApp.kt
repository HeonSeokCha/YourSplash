package com.chs.yoursplash.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.yoursplash.presentation.bottom.BottomBar
import com.chs.yoursplash.presentation.main.MainNavHost
import com.chs.yoursplash.presentation.main.MainTopBar
import com.chs.yoursplash.presentation.main.MainViewModel
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun YourSplashApp(onNavigate: (Pair<String, String>) -> Unit) {
    val navController: NavHostController = rememberNavController()
    val viewModel = koinViewModel<MainViewModel>()
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
                        viewModel.updateSearchQuery(it)
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
                searchQuery = state.searchQuery,
                onBack = { viewModel.updateSearchQuery("") },
                onNavigate = onNavigate
            )
        }
    }
}