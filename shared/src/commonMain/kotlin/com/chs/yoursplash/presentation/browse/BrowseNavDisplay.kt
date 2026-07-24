package com.chs.yoursplash.presentation.browse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import com.chs.yoursplash.presentation.base.YourNavDisplay
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
fun BrowseNavDisplay(
    modifier: Modifier,
    type: String,
    id: String,
    onBrowser: (String) -> Unit,
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

    YourNavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<BrowseScreens.PhotoDetailScreen> { key ->
                val viewModel: PhotoDetailViewModel = koinViewModel<PhotoDetailViewModel> {
                    parametersOf(key.photoId)
                }

                PhotoDetailScreenRoot(
                    viewModel = viewModel,
                    onNavigate = { backStack.add(it) },
                    onBrowser = { onBrowser(Constants.PHOTO_SOURCE_URL(it)) },
                    onClose = onBack
                )
            }

            entry<BrowseScreens.CollectionDetailScreen> { key ->
                val viewModel: CollectionDetailViewModel = koinViewModel<CollectionDetailViewModel> {
                    parametersOf(key.collectionId)
                }

                CollectionDetailScreenRoot(
                    viewModel = viewModel,
                    onNavigate = { backStack.add(it) },
                    onBrowser = { onBrowser(Constants.COLLECTION_SOURCE_URL(it)) },
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