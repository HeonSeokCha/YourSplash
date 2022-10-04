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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.browse.image_detail.ImageDetailScreen
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import com.chs.yoursplash.presentation.browse.user.UserDetailScreen
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            YourSplashTheme {

                val startMediaDestination =
                    when (intent?.getStringExtra(Constants.TARGET_TYPE)) {
                        Constants.TARGET_PHOTO -> {
                            "${Screens.ImageDetailScreen.route}/{id}"
                        }
                        Constants.TARGET_COLLECTION -> {
                            "${Screens.CollectionDetailScreen.route}/{id}"
                        }
                        else -> {
                            "${Screens.UserDetailScreen.route}/{id}"
                        }
                    }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column {
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
                                "${Screens.UserDetailScreen.route}/{id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.StringType
                                        defaultValue = intent.getStringExtra(Constants.TARGET_ID)!!
                                    }
                                )
                            ) { backStackEntry ->
                                UserDetailScreen(
                                    userId = backStackEntry.arguments?.getString("id")!!,
                                    navController = navController
                                )
                            }
                        }
                    }
                    ImageDetailTopBar()
                }
            }
        }
    }
}


@Composable
fun ImageDetailTopBar() {
    val activity = (LocalContext.current as? Activity)
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = {
                activity?.finish()
            }) {
                Icon(Icons.Filled.Close, contentDescription = null)
            }
        }, backgroundColor = Color.Transparent,
        elevation = 0 .dp,
        contentColor = Color.White
    )
}