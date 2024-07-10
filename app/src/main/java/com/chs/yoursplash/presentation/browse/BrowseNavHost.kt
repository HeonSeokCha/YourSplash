package com.chs.yoursplash.presentation.browse

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
import com.chs.yoursplash.presentation.main.MainScreens
import com.chs.yoursplash.util.Constants

@Composable
fun BrowseNavHost(
    modifier: Modifier,
    navController: NavHostController,
    intent: Intent?
) {
    val startDestination =
        when (intent?.getStringExtra(Constants.TARGET_TYPE)) {
            Constants.TARGET_PHOTO -> {
                Screens.ImageDetailScreen(intent.getStringExtra(Constants.TARGET_ID)!!)
            }

            Constants.TARGET_COLLECTION -> {
                Screens.CollectionDetailScreen(intent.getStringExtra(Constants.TARGET_ID)!!)
            }

            Constants.TARGET_USER -> {
                Screens.UserDetailScreen(intent?.getStringExtra(Constants.TARGET_ID)!!)
            }

            else -> Unit
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
            val viewModel: PhotoDetailViewModel = hiltViewModel(parentEntry)
            ImageDetailScreen(
                state = viewModel.state,
                navController = navController
            )
        }

        composable<Screens.CollectionDetailScreen> {
            val arg = it.toRoute<Screens.CollectionDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: CollectionDetailViewModel = hiltViewModel(parentEntry)
            CollectionDetailScreen(
                state = viewModel.state,
                navController = navController
            )
        }

        composable<Screens.UserDetailScreen> {
            val arg = it.toRoute<Screens.UserDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: UserDetailViewModel = hiltViewModel(parentEntry)
            UserDetailScreen(
                state = viewModel.state,
                onNavigate = { navController.navigate(it) }
            )
        }

        composable<Screens.PhotoTagResultScreen> {
            val arg = it.toRoute<Screens.PhotoTagResultScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: PhotoTagListViewModel = hiltViewModel(parentEntry)
            PhotoTagListScreen(
                state = viewModel.state,
                navController = navController
            )
        }
    }
}