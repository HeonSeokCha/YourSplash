package com.chs.yoursplash.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.chs.yoursplash.presentation.bottom.BottomBar
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import dagger.hilt.android.AndroidEntryPoint

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
