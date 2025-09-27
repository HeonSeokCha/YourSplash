package com.chs.yoursplash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chs.yoursplash.data.model.ResponsePhotoUrls
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.PhotoUrls

@Composable
fun Int.pxToDp(): Dp {
    (this / LocalDensity.current.density).dp.run {
        return this
    }
}

fun Int.toCommaFormat(): String = this.toString().reversed().chunked(3).joinToString(",")
    .reversed()

fun String?.toComposeColor(): Color {
    return try {
        if (this == null) return Color.LightGray

        val cleanHex = this.removePrefix("#").uppercase()

        when (cleanHex.length) {
            6 -> {
                val colorLong = cleanHex.toLong(16)
                Color(
                    red = ((colorLong and 0xFF0000) shr 16).toInt(),
                    green = ((colorLong and 0x00FF00) shr 8).toInt(),
                    blue = (colorLong and 0x0000FF).toInt(),
                    alpha = 255
                )
            }
            8 -> {
                val colorLong = cleanHex.toLong(16)
                Color(
                    alpha = ((colorLong and 0xFF000000) shr 24).toInt(),
                    red = ((colorLong and 0x00FF0000) shr 16).toInt(),
                    green = ((colorLong and 0x0000FF00) shr 8).toInt(),
                    blue = (colorLong and 0x000000FF).toInt()
                )
            }
            else -> {
                Color.LightGray
            }
        }
    } catch (e: Exception) {
        Color.LightGray
    }
}

fun PhotoUrls.toSettingUrl(loadQuality: LoadQuality): String? {
    return when (loadQuality) {
        LoadQuality.Raw -> this.raw
        LoadQuality.Full -> this.full
        LoadQuality.Regular -> this.regular
        LoadQuality.Small -> this.small
        LoadQuality.Thumb -> this.thumb
    }
}