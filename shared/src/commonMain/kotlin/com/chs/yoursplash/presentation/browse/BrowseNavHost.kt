package com.chs.yoursplash.presentation.browse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailScreenRoot
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailScreenRoot
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailViewScreen
import com.chs.yoursplash.presentation.browse.photo_tag.PhotoTagListScreenRoot
import com.chs.yoursplash.presentation.browse.photo_tag.PhotoTagListViewModel
import com.chs.yoursplash.presentation.browse.user.UserDetailScreenRoot
import com.chs.yoursplash.presentation.browse.user.UserDetailViewModel
import com.chs.yoursplash.util.Constants
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BrowseNavHost(
    modifier: Modifier,
    type: String,
    id: String,
    onBack: () -> Unit
) {
    val startDestination = when(type) {
        Constants.TARGET_PHOTO -> {
            BrowseScreens.PhotoDetailScreen(id)
        }

        Constants.TARGET_COLLECTION -> {
            BrowseScreens.CollectionDetailScreen(id)
        }

        Constants.TARGET_USER -> {
            BrowseScreens.UserDetailScreen(id)
        }

        else -> {
            BrowseScreens.PhotoDetailScreen(id)
        }
    }

    val backStack: SnapshotStateList<BrowseScreens> = remember { mutableStateListOf(startDestination) }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<BrowseScreens.PhotoDetailScreen> { key ->
                val viewModel: PhotoDetailViewModel = koinViewModel<PhotoDetailViewModel> {
                    parametersOf(key.photoId)
                }

                PhotoDetailScreenRoot(
                    viewModel = viewModel,
                    onNavigate = { backStack.add(it) },
                    onClose = onBack
                )
            }

            entry<BrowseScreens.CollectionDetailScreen> { key ->
                val viewModel: CollectionDetailViewModel = koinViewModel<CollectionDetailViewModel> {
                    parametersOf(key.collectionId)
                }

                CollectionDetailScreenRoot(
                    viewModel = viewModel,
                    onBrowse = { backStack.removeLastOrNull() },
                    onClose = onBack
                )
            }

            entry<BrowseScreens.UserDetailScreen> { key ->
                val viewModel: UserDetailViewModel = koinViewModel<UserDetailViewModel> {
                    parametersOf(key.userName)
                }
                UserDetailScreenRoot(
                    viewModel = viewModel,
                    onClose = onBack,
                    onNavigate = { backStack.add(it) }
                )
            }

            entry<BrowseScreens.PhotoTagResultScreen> { key ->
                val viewModel: PhotoTagListViewModel = koinViewModel<PhotoTagListViewModel> {
                    parametersOf(key.tagName)
                }

                PhotoTagListScreenRoot(
                    viewModel = viewModel,
                    onClose = onBack,
                    onNavigate = { backStack.add(it) }
                )
            }

            entry<BrowseScreens.PhotoDetailViewScreen> { key ->
                PhotoDetailViewScreen(key.url)
            }
        }
    )
}