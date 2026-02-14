package com.chs.yoursplash.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropLandscape
import androidx.compose.material.icons.filled.CropPortrait
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.ui.graphics.vector.ImageVector

enum class Orientations(
    val rawValue: String?,
    val icon: ImageVector?
) {
    Any(null, null),
    LandScape("landscape", Icons.Default.CropLandscape),
    Portrait("portrait", Icons.Default.CropPortrait),
    Squarish("squarish", Icons.Default.CropSquare)
}