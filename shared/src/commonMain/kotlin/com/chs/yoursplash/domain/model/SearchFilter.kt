package com.chs.yoursplash.domain.model

data class SearchFilter(
    val orderBy: SortType = SortType.Relevance,
    val color: String? = null,
    val orientation: Orientations = Orientations.Any
)
