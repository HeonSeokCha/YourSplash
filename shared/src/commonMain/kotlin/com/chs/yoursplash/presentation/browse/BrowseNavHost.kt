package com.chs.yoursplash.presentation.browse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.presentation.Screens
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

@Composable
fun BrowseNavHost(
    modifier: Modifier,
    navController: NavHostController,
    type: String,
    id: String,
    onBack: () -> Unit
) {
    val startDestination: Screens = when (type) {
        Constants.TARGET_PHOTO -> {
            Screens.PhotoDetailScreen(id)
        }

        Constants.TARGET_COLLECTION -> {
            Screens.CollectionDetailScreen(id)
        }

        Constants.TARGET_USER -> {
            Screens.UserDetailScreen(id)
        }

        else -> {
            Screens.PhotoDetailScreen(id)
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screens.PhotoDetailScreen> {
            val arg = it.toRoute<Screens.PhotoDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: PhotoDetailViewModel = koinViewModel<PhotoDetailViewModel>(
                viewModelStoreOwner = parentEntry
            )

            PhotoDetailScreenRoot(
                viewModel = viewModel,
                onNavigate = { navController.navigate(it) },
                onClose = onBack
            )
        }

        composable<Screens.CollectionDetailScreen> {
            val arg = it.toRoute<Screens.CollectionDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: CollectionDetailViewModel = koinViewModel<CollectionDetailViewModel>(
                viewModelStoreOwner = parentEntry
            )

            CollectionDetailScreenRoot(
                viewModel = viewModel,
                onBrowse = { navController.navigate(it) },
                onClose = onBack
            )
        }

        composable<Screens.UserDetailScreen> {
            val arg = it.toRoute<Screens.UserDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: UserDetailViewModel = koinViewModel<UserDetailViewModel>(
                viewModelStoreOwner = parentEntry
            )

            UserDetailScreenRoot(
                viewModel = viewModel,
                onClose = onBack,
                onNavigate = { navController.navigate(it) }
            )
        }

        composable<Screens.PhotoTagResultScreen> {
            val arg = it.toRoute<Screens.PhotoTagResultScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: PhotoTagListViewModel = koinViewModel<PhotoTagListViewModel>(
                viewModelStoreOwner = parentEntry
            )

            PhotoTagListScreenRoot(
                viewModel = viewModel,
                onClose = onBack,
                onNavigate = { navController.navigate(it) }
            )
        }

        composable<Screens.PhotoDetailViewScreen> {
            val arg = it.toRoute<Screens.PhotoDetailViewScreen>()

            PhotoDetailViewScreen(arg.url)
        }
    }
}