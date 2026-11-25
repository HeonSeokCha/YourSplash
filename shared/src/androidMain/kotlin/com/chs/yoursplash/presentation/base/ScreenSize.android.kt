package com.chs.yoursplash.presentation.base

import coil3.PlatformContext

actual fun PlatformContext.screenWidthPx(): Int = resources.displayMetrics.widthPixels
