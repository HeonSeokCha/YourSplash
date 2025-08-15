package com.chs.yoursplash

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import com.chs.yoursplash.presentation.YourSplashApp

fun MainViewController() = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {

    val window = UIApplication.sharedApplication.keyWindow()
    val rootViewController = window?.rootViewController() as? UINavigationController

    YourSplashApp {
        val secondViewController = BrowseViewController(
            controller = rootViewController!!,
            info = it
        )

        rootViewController.pushViewController(
            viewController = secondViewController,
            animated = true
        )
    }
 }