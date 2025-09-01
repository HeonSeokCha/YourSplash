package com.chs.yoursplash.presentation.search

import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.SearchFilter

sealed class SearchEvent {
    data class BrowseScreen(val info: BrowseInfo) : SearchEvent()
    data object OnBack : SearchEvent()
    data class TabIndex(val idx: Int) : SearchEvent()
    data class OnChangeSearchQuery(val query: String) : SearchEvent()
    data class OnChangeSearchFilter(val filter : SearchFilter) : SearchEvent()
    data object OnChangeShowModal : SearchEvent()
    data object OnError : SearchEvent()
}