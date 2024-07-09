package com.chs.yoursplash.presentation.browse

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

@Composable
fun BrowseNavHost(
    modifier: Modifier,
    navController: NavHostController,
    intent: Intent?
) {
    val startDestination =
        when (intent?.getStringExtra(Constants.TARGET_TYPE)) {
            Constants.TARGET_PHOTO -> {
                Screens.ImageDetailScreen(Constants.ARG_KEY_PHOTO_ID)
            }

            Constants.TARGET_COLLECTION -> {
                Screens.CollectionDetailScreen(Constants.ARG_KEY_COLLECTION_ID)
            }

            else -> {
                Screens.UserDetailScreen(Constants.ARG_KEY_USER_NAME)
            }
        }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screens.ImageDetailScreen> {
            val viewModel: PhotoDetailViewModel = hiltViewModel()
            ImageDetailScreen(
                state = viewModel.state,
                navController = navController
            )
        }

        composable<Screens.CollectionDetailScreen> {
            val viewModel: CollectionDetailViewModel = hiltViewModel()
            CollectionDetailScreen(
                state = viewModel.state,
                navController = navController
            )
        }

        composable<Screens.UserDetailScreen> {
            val viewModel: UserDetailViewModel = hiltViewModel()
            UserDetailScreen(
                state = viewModel.state,
                onNavigate = { navController.navigate(it) }
            )
        }

        composable<Screens.PhotoTagResultScreen> {
            val viewModel: PhotoTagListViewModel = hiltViewModel()
            PhotoTagListScreen(
                state = viewModel.state,
                navController = navController
            )
        }
    }
}