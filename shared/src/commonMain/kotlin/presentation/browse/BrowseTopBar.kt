package com.chs.yoursplash.presentation.browse

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chs.yoursplash.presentation.Screens
import util.fromScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailTopBar(navController: NavHostController) {
    val activity = (LocalContext.current as? Activity)
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.fromScreenRoute()
    when (currentDestination) {
        is Screens.PhotoTagResultScreen -> {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
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
