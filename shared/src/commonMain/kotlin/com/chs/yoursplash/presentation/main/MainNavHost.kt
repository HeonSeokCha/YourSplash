package com.chs.yoursplash.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    searchQuery: String,
    onNavigate: (Pair<String, String>) -> Unit,
    onBack: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainScreens.HomeScreen
    ) {
        composable<MainScreens.HomeScreen> {
            val viewModel = koinViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            HomeScreen(
                state = state,
            ) { info ->
                onNavigate(info)
            }
        }

        composable<MainScreens.CollectionScreen> {
            val viewModel = koinViewModel<CollectionViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            CollectionScreen(state = state) {
                onNavigate(it)
            }
        }

        composable<MainScreens.SearchScreen> {
            val viewModel = koinViewModel<SearchResultViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(searchQuery) {
                snapshotFlow { searchQuery }
                    .distinctUntilChanged()
                    .filterNot { it.isEmpty() }
                    .collect {
                        viewModel.searchResult(it)
                    }
            }

            SearchScreen(
                state = state,
                modalClick = { },
                onBack = onBack
            ) {
                onNavigate(it)
            }
        }

        composable<MainScreens.SettingScreen> {
            val viewModel = koinViewModel<SettingViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            SettingScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}