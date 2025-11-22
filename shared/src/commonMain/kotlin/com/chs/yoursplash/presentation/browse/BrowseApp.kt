package com.chs.yoursplash.presentation.browse

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BrowseApp(
    info: Pair<String, String>,
    onBack: () -> Unit
) {
    BrowseNavHost(
        modifier = Modifier
            .fillMaxSize(),
        type = info.first,
        id = info.second,
        onBack = onBack
    )
}