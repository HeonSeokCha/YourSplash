package com.chs.yoursplash.presentation.browse

import android.content.Intent
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
    modifier: Modifier,
    navController: NavHostController,
    intent: Intent?
) {
    val startDestination =
        when (intent?.getStringExtra(Constants.TARGET_TYPE)) {
            Constants.TARGET_PHOTO -> {
                "${Screens.ImageDetailScreen.route}/${intent.getStringExtra(Constants.TARGET_ID)}"
            }

            Constants.TARGET_COLLECTION -> {
                "${Screens.CollectionDetailScreen.route}/${intent.getStringExtra(Constants.TARGET_ID)}"
            }

            else -> {
                "${Screens.UserDetailScreen.route}/${intent?.getStringExtra(Constants.TARGET_ID)}"
            }
        }

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
                    defaultValue = intent?.getStringExtra(Constants.TARGET_ID)!!
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
                    defaultValue = intent?.getStringExtra(Constants.TARGET_ID)!!
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
                    defaultValue = intent?.getStringExtra(Constants.TARGET_ID)!!
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
                    defaultValue = intent?.getStringExtra(Constants.TARGET_ID)!!
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