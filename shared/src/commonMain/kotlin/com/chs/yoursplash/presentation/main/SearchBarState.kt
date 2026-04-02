package com.chs.yoursplash.presentation.main

data class SearchBarState(
    val searchHistory: List<String> = emptyList(),
    val isShowDialog: Boolean = false,
    val deleteText: String = ""
)
