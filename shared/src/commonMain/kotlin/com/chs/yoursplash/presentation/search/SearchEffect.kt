package com.chs.yoursplash.presentation.search

import com.chs.yoursplash.domain.model.BrowseInfo


sealed interface SearchEffect {
    data class NavigateBrowse(val info: BrowseInfo) : SearchEffect
    data object OnBack : SearchEffect
}