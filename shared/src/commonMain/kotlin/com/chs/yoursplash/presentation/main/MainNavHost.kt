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
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.bottom.collection.CollectionScreenRoot
import com.chs.yoursplash.presentation.bottom.collection.CollectionViewModel
import com.chs.yoursplash.presentation.bottom.home.HomeScreenRoot
import com.chs.yoursplash.presentation.bottom.home.HomeViewModel
import com.chs.yoursplash.presentation.search.SearchIntent
import com.chs.yoursplash.presentation.search.SearchResultViewModel
import com.chs.yoursplash.presentation.search.SearchScreenRoot
import com.chs.yoursplash.presentation.setting.SettingScreen
import com.chs.yoursplash.presentation.setting.SettingViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    searchQuery: String,
    onBrowse: (BrowseInfo) -> Unit,
    onBack: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainScreens.HomeScreen
    ) {
        composable<MainScreens.HomeScreen> {
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreenRoot(
                viewModel = viewModel,
                onBrowse = onBrowse
            )
        }

        composable<MainScreens.CollectionScreen> {
            val viewModel = koinViewModel<CollectionViewModel>()
            CollectionScreenRoot(
                viewModel = viewModel,
                onBrowse = onBrowse
            )
        }

        composable<MainScreens.SearchScreen> {
            val viewModel = koinViewModel<SearchResultViewModel>()
            LaunchedEffect(searchQuery) {
                snapshotFlow { searchQuery }
                    .distinctUntilChanged()
                    .filter { it.isNotEmpty() }
                    .collect {
                        viewModel.changeEvent(SearchIntent.ChangeSearchQuery(it))
                    }
            }

            SearchScreenRoot(
                viewModel = viewModel,
                onBack= onBack,
                onBrowse = onBrowse
            )
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