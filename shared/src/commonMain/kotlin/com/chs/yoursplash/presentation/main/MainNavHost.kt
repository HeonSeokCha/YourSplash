package com.chs.yoursplash.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.bottom.collection.CollectionScreenRoot
import com.chs.yoursplash.presentation.bottom.collection.CollectionViewModel
import com.chs.yoursplash.presentation.bottom.photo.PhotoScreenRoot
import com.chs.yoursplash.presentation.bottom.photo.PhotoViewModel
import com.chs.yoursplash.presentation.search.SearchIntent
import com.chs.yoursplash.presentation.search.SearchResultViewModel
import com.chs.yoursplash.presentation.search.SearchScreenRoot
import com.chs.yoursplash.presentation.setting.SettingScreenRoot
import com.chs.yoursplash.presentation.setting.SettingViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    backStack: SnapshotStateList<MainScreens>,
    searchQuery: String,
    onBrowse: (BrowseInfo) -> Unit,
) {
    LaunchedEffect(backStack.count()) {
        println(backStack.toString())
    }
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
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

            entry<MainScreens.SearchScreen> {
                val viewModel = koinViewModel<SearchResultViewModel>()
                LaunchedEffect(searchQuery) {
                    snapshotFlow { searchQuery }
                        .distinctUntilChanged()
                        .filter { it.isNotEmpty() }
                        .collect {
                            println(searchQuery)
                            viewModel.changeEvent(SearchIntent.ChangeSearchQuery(it))
                        }
                }

                SearchScreenRoot(
                    viewModel = viewModel,
                    onBrowse = onBrowse
                )
            }

            entry<MainScreens.SettingScreen> {
                val viewModel = koinViewModel<SettingViewModel>()

                SettingScreenRoot(viewModel)
            }
        }
    )
}