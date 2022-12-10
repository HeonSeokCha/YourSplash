package com.chs.yoursplash.util

data class SearchFilter(
    val orderBy: String = "relevant",
    val color: String? = null,
    val orientation: String? = null
)
