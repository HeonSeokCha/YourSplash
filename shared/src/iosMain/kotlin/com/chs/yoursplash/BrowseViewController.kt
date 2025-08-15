package com.chs.yoursplash

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UINavigationController
import com.chs.yoursplash.presentation.browse.BrowseApp

fun BrowseViewController(
    controller: UINavigationController,
    info: Pair<String, String>
) = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {

    BrowseApp(
        info = info,
        onBack = {
            controller.popViewControllerAnimated(true)
        }
    )
}
