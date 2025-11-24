package com.chs.yoursplash.presentation.browse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.chs.yoursplash.presentation.BrowseScreens
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailScreenRoot
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailViewScreen
import com.chs.yoursplash.presentation.browse.photo_tag.PhotoTagListScreenRoot
import com.chs.yoursplash.presentation.browse.photo_tag.PhotoTagListViewModel
import com.chs.yoursplash.presentation.browse.user.UserDetailScreenRoot
import com.chs.yoursplash.presentation.browse.user.UserDetailViewModel
import com.chs.yoursplash.util.Constants
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BrowseNavHost(
    modifier: Modifier,
    type: String,
    id: String,
    onBack: () -> Unit
) {
    val module = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(BrowseScreens.PhotoDetailScreen::class, BrowseScreens.PhotoDetailScreen.serializer())
            subclass(BrowseScreens.PhotoDetailViewScreen::class, BrowseScreens.PhotoDetailViewScreen.serializer())
            subclass(BrowseScreens.CollectionDetailScreen::class, BrowseScreens.CollectionDetailScreen.serializer())
            subclass(BrowseScreens.UserDetailScreen::class, BrowseScreens.UserDetailScreen.serializer())
            subclass(BrowseScreens.PhotoTagResultScreen::class, BrowseScreens.PhotoTagResultScreen.serializer())
        }
    }

    val config = SavedStateConfiguration { serializersModule = module }
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
    val backStack = rememberNavBackStack(configuration = config, startDestination)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<BrowseScreens.PhotoDetailScreen> {
                val viewModel: PhotoDetailViewModel = koinViewModel<PhotoDetailViewModel>()

                PhotoDetailScreenRoot(
                    viewModel = viewModel,
                    onNavigate = { backStack.add(it) },
                    onClose = onBack
                )
            }

            entry<BrowseScreens.CollectionDetailScreen> {
                val viewModel: CollectionDetailViewModel = koinViewModel<CollectionDetailViewModel>()

//                CollectionDetailScreenRoot(
//                    viewModel = viewModel,
//                    onBrowse = { navController.navigate(it) },
//                    onClose = onBack
//                )
            }

            entry<BrowseScreens.UserDetailScreen> {
                val viewModel: UserDetailViewModel = koinViewModel<UserDetailViewModel>()
                UserDetailScreenRoot(
                    viewModel = viewModel,
                    onClose = onBack,
                    onNavigate = { backStack.add(it) }
                )
            }

            entry<BrowseScreens.PhotoTagResultScreen> {
                val viewModel: PhotoTagListViewModel = koinViewModel<PhotoTagListViewModel>()

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