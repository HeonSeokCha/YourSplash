package com.chs.yoursplash.domain.model

data class SearchFilter(
    val query: String,
    val orderBy: String = "relevant",
    val color: String? = null,
    val orientation: String? = null
)
