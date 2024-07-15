package com.chs.yoursplash.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chs.yoursplash.presentation.bottom.collection.CollectionScreen
import com.chs.yoursplash.presentation.bottom.collection.CollectionViewModel
import com.chs.yoursplash.presentation.bottom.home.HomeScreen
import com.chs.yoursplash.presentation.bottom.home.HomeViewModel
import com.chs.yoursplash.presentation.search.SearchResultViewModel
import com.chs.yoursplash.presentation.search.SearchScreen
import com.chs.yoursplash.presentation.setting.SettingScreen
import com.chs.yoursplash.presentation.setting.SettingViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot

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
            val parentEntry = remember(it) {
                navController.getBackStackEntry(MainScreens.SearchScreen)
            }
            val viewModel: SearchResultViewModel = hiltViewModel(parentEntry)

            LaunchedEffect(searchQuery) {
                snapshotFlow { searchQuery }
                    .distinctUntilChanged()
                    .filterNot{ it.isEmpty() }
                    .collect {
                        viewModel.searchResult(it)
                    }
            }

            SearchScreen(
                state = viewModel.state,
                modalClick = { },
                onBack = onBack
            )
        }

        composable<MainScreens.SettingScreen> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(MainScreens.SettingScreen)
            }
            val viewModel: SettingViewModel = hiltViewModel(parentEntry)
            SettingScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }
    }
}