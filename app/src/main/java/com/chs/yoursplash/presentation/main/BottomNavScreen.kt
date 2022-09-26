package com.chs.yoursplash.presentation.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.chs.yoursplash.R

sealed class BottomNavScreen(
    val route: String,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    object HomeScreen : BottomNavScreen("home_screen", Icons.Default.Home, R.string.home)
    object CollectionScreen : BottomNavScreen("collection_screen", Icons.Default.PermMedia, R.string.collection)
}