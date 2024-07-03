package com.chs.yoursplash.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chs.yoursplash.presentation.bottom.collection.CollectionScreen
import com.chs.yoursplash.presentation.bottom.collection.CollectionViewModel
import com.chs.yoursplash.presentation.bottom.home.HomeScreen
import com.chs.yoursplash.presentation.bottom.home.HomeViewModel
import com.chs.yoursplash.presentation.search.SearchScreen
import com.chs.yoursplash.presentation.setting.SettingScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    searchQuery: String,
    onBack: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainScreens.HomeScreen
    ) {
        composable<MainScreens.HomeScreen> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(MainScreens.HomeScreen)
            }
            val viewModel: HomeViewModel = hiltViewModel(parentEntry)
            HomeScreen(viewModel.state)
        }

        composable<MainScreens.CollectionScreen> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(MainScreens.CollectionScreen)
            }
            val viewmodel: CollectionViewModel = hiltViewModel(parentEntry)
            CollectionScreen(viewmodel.state) { }
        }

        composable<MainScreens.SearchScreen> {
            SearchScreen(
                searchQuery = searchQuery,
                onBack = onBack
            )
        }

        composable<MainScreens.SettingScreen> {
            SettingScreen()
        }
    }
}