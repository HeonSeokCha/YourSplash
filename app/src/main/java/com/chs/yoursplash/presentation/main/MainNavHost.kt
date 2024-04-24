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
        startDestination = BottomNavScreen.HomeScreen.route
    ) {
        composable(BottomNavScreen.HomeScreen.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(BottomNavScreen.HomeScreen.route)
            }
            val viewModel: MainViewModel = hiltViewModel(parentEntry)
            HomeScreen(viewModel.state) {

            }
        }

        composable(BottomNavScreen.CollectionScreen.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(BottomNavScreen.CollectionScreen.route)
            }
            val viewmodel: CollectionViewModel = hiltViewModel(parentEntry)
            CollectionScreen(viewmodel.state) {

            }
        }

        composable(MainScreens.SearchScreen.route) {
            SearchScreen(
                searchQuery = searchQuery,
                onBack = onBack
            )
        }

        composable(MainScreens.SettingScreen.route) {
            SettingScreen()
        }
    }
}