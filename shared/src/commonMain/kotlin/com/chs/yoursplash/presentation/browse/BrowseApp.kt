package com.chs.yoursplash.presentation.browse

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chs.yoursplash.domain.model.BrowseInfo

@Composable
fun BrowseApp(
    info: Pair<String, String>,
    onBack: () -> Unit
) {
    val navController: NavHostController = rememberNavController()
    BrowseNavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        type = info.first,
        id = info.second,
        onBack = onBack
    )
}