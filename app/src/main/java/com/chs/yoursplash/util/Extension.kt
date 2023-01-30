package com.chs.yoursplash.util

import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


val String.color
    get() =  Color(parseColor(this))


fun <T> LazyListScope.gridItems(
    data: List<T>,
    colCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable BoxScope.(T) -> Unit
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / colCount
    items(rows, key = { it.hashCode() }) { rowIdx ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (colIdx in 0 until colCount) {
                val itemIdx = rowIdx * colCount + colIdx
                if (itemIdx < size) {
                    Box(
                        modifier = Modifier.weight(1f, fill = true),
                        propagateMinConstraints = true
                    ) {
                        content(data[itemIdx])
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f, fill = true))
                }
            }
        }
    }
}



private fun getShortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var col = 0
    colHeights.forEachIndexed { idx, height ->
        if (height < minHeight) {
            minHeight = height
            col = idx
        }
    }
    return col
}