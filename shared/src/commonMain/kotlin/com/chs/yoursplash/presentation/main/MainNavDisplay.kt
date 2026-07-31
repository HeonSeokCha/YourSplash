package com.chs.yoursplash.presentation.main

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.NavDirection
import com.chs.yoursplash.presentation.bottom.BottomTopLevelBackStack
import com.chs.yoursplash.presentation.bottom.collection.CollectionScreenRoot
import com.chs.yoursplash.presentation.bottom.collection.CollectionViewModel
import com.chs.yoursplash.presentation.bottom.favorite.FavoriteScreenRoot
import com.chs.yoursplash.presentation.bottom.favorite.FavoriteViewModel
import com.chs.yoursplash.presentation.bottom.photo.PhotoScreenRoot
import com.chs.yoursplash.presentation.bottom.photo.PhotoViewModel
import com.chs.yoursplash.presentation.directionalTransform
import com.chs.yoursplash.presentation.search.SearchResultViewModel
import com.chs.yoursplash.presentation.search.SearchScreenRoot
import com.chs.yoursplash.presentation.setting.SettingScreenRoot
import com.chs.yoursplash.presentation.setting.SettingViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNavDisplay(
    modifier: Modifier = Modifier,
    backStack: BottomTopLevelBackStack,
    onBrowse: (BrowseInfo) -> Unit,
    searchQuery: String
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack.backStack,
        onBack = { backStack.removeLast() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = { directionalTransform(backStack.direction) },
        popTransitionSpec = { directionalTransform(backStack.direction) },
        predictivePopTransitionSpec = { directionalTransform(NavDirection.BACKWARD) },
        entryProvider = entryProvider {
            entry<MainScreens.PhotoScreen> {
                val viewModel = koinViewModel<PhotoViewModel>()
                PhotoScreenRoot(
                    viewModel = viewModel,
                    onBrowse = onBrowse
                )
            }

            entry<MainScreens.CollectionScreen> {
                val viewModel = koinViewModel<CollectionViewModel>()
                CollectionScreenRoot(
                    viewModel = viewModel,
                    onBrowse = onBrowse
                )
            }

            entry<MainScreens.FavoriteScreen> {
                val viewModel = koinViewModel<FavoriteViewModel>()
                FavoriteScreenRoot(
                    viewModel = viewModel,
                    onBrowse = onBrowse
                )
            }

            entry<MainScreens.SearchScreen> {
                val viewModel = koinViewModel<SearchResultViewModel>()

                LaunchedEffect(searchQuery) {
                    snapshotFlow { searchQuery }
                        .distinctUntilChanged()
                        .filter { it.isNotEmpty() }
                        .collect { viewModel.changeQuery(searchQuery) }
                }

                SearchScreenRoot(
                    viewModel = viewModel,
                    onBrowse = onBrowse,
                    onBack = { backStack.removeLast() }
                )
            }

            entry<MainScreens.SettingScreen> {
                val viewModel = koinViewModel<SettingViewModel>()

                SettingScreenRoot(viewModel)
            }
        }
    )
}