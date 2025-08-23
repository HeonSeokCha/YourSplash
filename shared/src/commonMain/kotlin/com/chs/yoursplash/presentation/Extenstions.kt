package com.chs.yoursplash.presentation

import androidx.compose.runtime.Composable
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

fun ResponsePhotoUrls.toCurrentUrls(value: LoadQuality): String {
    return when (value) {
        LoadQuality.Raw -> this.raw
        LoadQuality.Full -> this.full
        LoadQuality.Regular -> this.regular
        LoadQuality.Small -> this.small
        LoadQuality.Thumb -> this.thumb
    }
}