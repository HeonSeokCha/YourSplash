package com.chs.yoursplash.presentation.browse.photo_detail

data class PhotoTagListState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
