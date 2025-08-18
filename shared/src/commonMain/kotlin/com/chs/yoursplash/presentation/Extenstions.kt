package com.chs.yoursplash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Int.pxToDp(): Dp {
    (this / LocalDensity.current.density).dp.run {
        return this
    }
}