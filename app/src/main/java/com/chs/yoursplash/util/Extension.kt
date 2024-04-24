package com.chs.yoursplash.util

import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


val String.color
    get() =  Color(parseColor(this))