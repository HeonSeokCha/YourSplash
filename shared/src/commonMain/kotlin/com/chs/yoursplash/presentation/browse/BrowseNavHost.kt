package com.chs.yoursplash.presentation.browse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailScreen
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.ImageDetailScreen
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoTagListScreen
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoTagListViewModel
import com.chs.yoursplash.presentation.browse.user.UserDetailScreen
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
            Screens.ImageDetailScreen(id)
        }

        Constants.TARGET_COLLECTION -> {
            Screens.CollectionDetailScreen(id)
        }

        Constants.TARGET_USER -> {
            Screens.UserDetailScreen(id)
        }

        else -> {
            Screens.ImageDetailScreen(id)
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screens.ImageDetailScreen> {
            val arg = it.toRoute<Screens.ImageDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: PhotoDetailViewModel = koinViewModel<PhotoDetailViewModel>(
                viewModelStoreOwner = parentEntry
            )
            val state by viewModel.state.collectAsStateWithLifecycle()

            ImageDetailScreen(
                state = state,
                onClose = onBack
            ) {
                navController.navigate(it)
            }
        }

        composable<Screens.CollectionDetailScreen> {
            val arg = it.toRoute<Screens.CollectionDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: CollectionDetailViewModel = koinViewModel<CollectionDetailViewModel>(
                viewModelStoreOwner = parentEntry
            )
            val state by viewModel.state.collectAsStateWithLifecycle()

            CollectionDetailScreen(
                state = state,
                onClose = onBack
            ) {
                if (it.first == Constants.TARGET_USER) {
                    navController.navigate(Screens.UserDetailScreen(it.second))
                } else {
                    navController.navigate(Screens.CollectionDetailScreen(it.second))
                }
            }
        }

        composable<Screens.UserDetailScreen> {
            val arg = it.toRoute<Screens.UserDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: UserDetailViewModel = koinViewModel<UserDetailViewModel>(
                viewModelStoreOwner = parentEntry
            )
            val state by viewModel.state.collectAsStateWithLifecycle()

            UserDetailScreen(
                state = state,
                onClose = onBack
            ) {
                navController.navigate(it)
            }
        }

        composable<Screens.PhotoTagResultScreen> {
            val arg = it.toRoute<Screens.PhotoTagResultScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: PhotoTagListViewModel = koinViewModel<PhotoTagListViewModel>(
                viewModelStoreOwner = parentEntry
            )
            PhotoTagListScreen(
                state = viewModel.state,
                onClose = onBack
            ) {
                if (it.first == Constants.TARGET_USER) {
                    navController.navigate(Screens.UserDetailScreen(it.second))
                } else {
                    navController.navigate(Screens.ImageDetailScreen(it.second))
                }
            }
        }
    }
}