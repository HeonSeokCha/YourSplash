package com.chs.yoursplash.presentation.browse

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.setSingletonImageLoaderFactory
import com.chs.yoursplash.presentation.base.newImageLoader

@Composable
fun BrowseApp(
    info: Pair<String, String>,
    onBrowser: (String) -> Unit,
    onBack: () -> Unit
) {
    setSingletonImageLoaderFactory(::newImageLoader)

    BrowseNavDisplay(
        modifier = Modifier
            .fillMaxSize(),
        type = info.first,
        id = info.second,
        onBrowser = onBrowser,
        onBack = onBack
    )
}