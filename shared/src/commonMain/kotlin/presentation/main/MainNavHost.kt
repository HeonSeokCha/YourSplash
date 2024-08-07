package presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import presentation.bottom.collection.CollectionScreen
import presentation.bottom.collection.CollectionViewModel
import presentation.bottom.home.HomeScreen
import presentation.bottom.home.HomeViewModel
import presentation.search.SearchResultViewModel
import presentation.search.SearchScreen
import presentation.setting.SettingScreen
import presentation.setting.SettingViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import org.koin.compose.viewmodel.koinViewModel
import util.Navigator

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    searchQuery: String,
    navigator: Navigator,
    onBack: () -> Unit
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainScreens.HomeScreen
    ) {
        composable<MainScreens.HomeScreen> {
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                state = viewModel.state,
            ) { info ->
                navigator.navigateToSecondActivity(
                    type = info.first,
                    id = info.second
                )
            }
        }

        composable<MainScreens.CollectionScreen> {
            val viewModel = koinViewModel<CollectionViewModel>()
            CollectionScreen(state = viewModel.state) {
                navigator.navigateToSecondActivity(
                    type = it.first,
                    id = it.second
                )
            }
        }

        composable<MainScreens.SearchScreen> {
            val viewModel = koinViewModel<SearchResultViewModel>()
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
            ) {
                navigator.navigateToSecondActivity(
                    type = it.first,
                    id = it.second
                )
            }
        }

        composable<MainScreens.SettingScreen> {
            val viewModel = koinViewModel<SettingViewModel>()
            SettingScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }
    }
}