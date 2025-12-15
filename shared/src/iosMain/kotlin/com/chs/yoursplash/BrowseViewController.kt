package com.chs.yoursplash

import androidx.compose.ui.window.ComposeUIViewController
import com.chs.yoursplash.domain.model.BrowseInfo
import platform.UIKit.UINavigationController
import com.chs.yoursplash.presentation.browse.BrowseApp
import com.chs.yoursplash.util.Constants
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import kotlin.collections.emptyMap

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
        onBrowser = {
            val nsUrl = NSURL.URLWithString(it)
            UIApplication.sharedApplication.openURL(nsUrl!!, emptyMap<Any?, Any>()) { success ->
                if (!success) {
                    println("Failed to open URL: $nsUrl")
                }
            }
        },
        onBack = { controller.popViewControllerAnimated(true) }
    )
}
