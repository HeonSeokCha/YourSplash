package com.chs.yoursplash.presentation.browse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailScreen
import com.chs.yoursplash.presentation.browse.photo_detail.ImageDetailScreen
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoTagListScreen
import com.chs.yoursplash.presentation.browse.user.UserDetailScreen
import com.chs.yoursplash.util.Constants

@Composable
fun BrowseNavHost(
    startDestination: String,
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            "${Screens.ImageDetailScreen.route}/{arg_key_photo_id}",
            arguments = listOf(
                navArgument(Constants.ARG_KEY_PHOTO_ID) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            ImageDetailScreen(
                navController = navController
            )
        }

        composable(
            "${Screens.CollectionDetailScreen.route}/{arg_key_collection_id}",
            arguments = listOf(
                navArgument(Constants.ARG_KEY_COLLECTION_ID) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            CollectionDetailScreen(
                navController = navController
            )
        }

        composable(
            "${Screens.UserDetailScreen.route}/{arg_key_user_name}",
            arguments = listOf(
                navArgument(Constants.ARG_KEY_USER_NAME) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            UserDetailScreen(
                navController = navController
            )
        }

        composable(
            "${Screens.PhotoTagResultScreen.route}/{arg_key_tag_name}",
            arguments = listOf(
                navArgument(Constants.ARG_KEY_TAG_NAME) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            PhotoTagListScreen(
                navController = navController
            )
        }
    }
}