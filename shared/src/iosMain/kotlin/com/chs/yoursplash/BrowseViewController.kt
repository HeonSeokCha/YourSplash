package com.chs.yoursplash

import androidx.compose.ui.window.ComposeUIViewController
import com.chs.yoursplash.domain.model.BrowseInfo
import platform.UIKit.UINavigationController
import com.chs.yoursplash.presentation.browse.BrowseApp

fun BrowseViewController(
    controller: UINavigationController,
    info: BrowseInfo
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
