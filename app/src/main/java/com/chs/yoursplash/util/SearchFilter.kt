package com.chs.yoursplash.util

data class SearchFilter(
    val orderBy: String = Constants.SORT_BY_LIST.keys.first(),
    val color: String = Constants.SEARCH_COLOR_LIST.keys.first(),
    val orientation: String = Constants.SEARCH_ORI_LIST.keys.first()
)
