package com.chs.yoursplash.presentation.browse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.ui.theme.YourSplashTheme
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            YourSplashTheme {
                Scaffold(
                    topBar = {
                        ImageDetailTopBar(navController)
                    }
                ) { padding ->
                    BrowseNavHost(
                        intent = intent,
                        modifier = Modifier.padding(padding),
                        navController = navController
                    )
                }
            }
        }
    }
}