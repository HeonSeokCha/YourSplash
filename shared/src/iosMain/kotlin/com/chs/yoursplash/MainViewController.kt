package com.chs.yoursplash

import androidx.compose.ui.window.ComposeUIViewController
import com.chs.yoursplash.domain.model.BrowseInfo
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import com.chs.yoursplash.presentation.YourSplashApp
import com.chs.yoursplash.util.Constants

fun MainViewController() = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
) {

    val window = UIApplication.sharedApplication.keyWindow()
    val rootViewController = window?.rootViewController() as? UINavigationController

    YourSplashApp {
        val info = when (it) {
            is BrowseInfo.Collection -> Constants.TARGET_COLLECTION to it.id

            is BrowseInfo.Photo -> Constants.TARGET_PHOTO to it.id

            is BrowseInfo.User -> Constants.TARGET_USER to it.name

            is BrowseInfo.PhotoTag -> Constants.TARGET_USER to it.tag
        }

        val secondViewController = BrowseViewController(
            controller = rootViewController!!,
            info = info
        )

        rootViewController.pushViewController(
            viewController = secondViewController,
            animated = true
        )
    }
 }