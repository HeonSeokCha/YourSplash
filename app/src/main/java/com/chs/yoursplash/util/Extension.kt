package com.chs.yoursplash.util

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color


val String.color
    get() =  Color(parseColor(this))

