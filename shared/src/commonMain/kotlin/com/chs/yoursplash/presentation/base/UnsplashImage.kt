package com.chs.yoursplash.presentation.base

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.map.Mapper
import coil3.request.Options
import coil3.size.pxOrElse

data class UnsplashImage(val rawUrl: String)

expect fun PlatformContext.screenWidthPx(): Int

class UnsplashImageMapper : Mapper<UnsplashImage, String> {
    override fun map(data: UnsplashImage, options: Options): String {
        val width = options.size.width.pxOrElse { options.context.screenWidthPx() }
        val bucketWidth = ((width + WIDTH_STEP - 1) / WIDTH_STEP) * WIDTH_STEP
        val separator = if ('?' in data.rawUrl) '&' else '?'
        return "${data.rawUrl}${separator}w=$bucketWidth&q=80&fm=jpg&fit=max"
    }

    private companion object {
        const val WIDTH_STEP = 100
    }
}

fun newImageLoader(context: PlatformContext): ImageLoader {
    return ImageLoader.Builder(context)
        .components { add(UnsplashImageMapper()) }
        .build()
}
