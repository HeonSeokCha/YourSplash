package com.chs.yoursplash.presentation.browse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailScreen
import com.chs.yoursplash.presentation.browse.photo_detail.ImageDetailScreen
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoTagListScreen
import com.chs.yoursplash.presentation.browse.user.UserDetailScreen
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val startMediaDestination =
                when (intent?.getStringExtra(Constants.TARGET_TYPE)) {
                    Constants.TARGET_PHOTO -> {
                        "${Screens.ImageDetailScreen.route}/{id}"
                    }

                    Constants.TARGET_COLLECTION -> {
                        "${Screens.CollectionDetailScreen.route}/{id}"
                    }

                    else -> {
                        "${Screens.UserDetailScreen.route}/{userName}"
                    }
                }
            YourSplashTheme {
                Scaffold(
                    topBar = {
                        ImageDetailTopBar(navController)
                    }
                ) { padding ->
                    BrowseNavHost(
                        startDestination = startMediaDestination,
                        modifier = Modifier.padding(padding),
                        navController = navController
                    )
                }
            }
        }
    }
}