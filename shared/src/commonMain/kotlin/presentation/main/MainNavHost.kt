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
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                state = viewModel.state,
            ) { info ->
//                context.startActivity(
//                    Intent(context, BrowseActivity::class.java).apply {
//                        putExtra(Constants.TARGET_TYPE, info.first)
//                        putExtra(Constants.TARGET_ID, info.second)
//                    }
//                )
            }
        }

        composable<MainScreens.CollectionScreen> {
            val viewModel = koinViewModel<CollectionViewModel>()
            CollectionScreen(state = viewModel.state) {
//                context.startActivity(
//                    Intent(context, BrowseActivity::class.java).apply {
//                        putExtra(Constants.TARGET_TYPE, it.first)
//                        putExtra(Constants.TARGET_ID, it.second)
//                    }
//                )
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
            )
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