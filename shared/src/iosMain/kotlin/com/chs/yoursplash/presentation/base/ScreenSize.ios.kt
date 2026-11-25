package com.chs.yoursplash.presentation.base

import coil3.PlatformContext
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
actual fun PlatformContext.screenWidthPx(): Int {
    val screen = UIScreen.mainScreen
    return (screen.bounds.useContents { size.width } * screen.scale).toInt()
}
