package com.chs.yoursplash.presentation.bottom.favorite

import com.chs.yoursplash.domain.model.FavoritePhoto

data class FavoriteState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val favoriteList: List<FavoritePhoto> = emptyList()
)
