package com.chs.yoursplash.presentation.browse

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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

    @OptIn(ExperimentalMaterial3Api::class)
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
                    Column(
                        modifier = Modifier.padding(padding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = startMediaDestination
                        ) {
                            composable(
                                "${Screens.ImageDetailScreen.route}/{id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.StringType
                                        defaultValue = intent.getStringExtra(Constants.TARGET_ID)!!
                                    }
                                )
                            ) { backStackEntry ->
                                ImageDetailScreen(
                                    photoId = backStackEntry.arguments?.getString("id")!!,
                                    navController = navController
                                )
                            }

                            composable(
                                "${Screens.CollectionDetailScreen.route}/{id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.StringType
                                        defaultValue = intent.getStringExtra(Constants.TARGET_ID)!!
                                    }
                                )
                            ) { backStackEntry ->
                                CollectionDetailScreen(
                                    collectionId = backStackEntry.arguments?.getString("id")!!,
                                    navController = navController
                                )
                            }

                            composable(
                                "${Screens.UserDetailScreen.route}/{userName}",
                                arguments = listOf(
                                    navArgument("userName") {
                                        type = NavType.StringType
                                        defaultValue = intent.getStringExtra(Constants.TARGET_ID)!!
                                    }
                                )
                            ) { backStackEntry ->
                                UserDetailScreen(
                                    userName = backStackEntry.arguments?.getString("userName")!!,
                                    navController = navController
                                )
                            }

                            composable(
                                "${Screens.PhotoTagResultScreen.route}/{tag}",
                                arguments = listOf(
                                    navArgument("tag") {
                                        type = NavType.StringType
                                        defaultValue = ""
                                    }
                                )
                            ) { backStackEntry ->
                                PhotoTagListScreen(
                                    tag = backStackEntry.arguments?.getString("tag")!!,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailTopBar(navController: NavHostController) {
    val activity = (LocalContext.current as? Activity)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        "${Screens.PhotoTagResultScreen.route}/{tag}"-> {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
        else -> {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        activity?.finish()
                    }) {
                        Icon(Icons.Filled.Close, contentDescription = null)
                    }
                },
            )
        }
    }
}