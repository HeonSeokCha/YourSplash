package com.chs.yoursplash.presentation.search

import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.SearchFilter

sealed interface SearchIntent {
    data class ChangeTabIndex(val idx: Int) : SearchIntent
    data class ChangeSearchQuery(val query: String) : SearchIntent
    data class ChangeSearchFilter(val filter : SearchFilter) : SearchIntent
    data object ChangeShowModal : SearchIntent
    data object ChangeExpandColorFilter : SearchIntent
    data class ClickBrowseInfo(val info: BrowseInfo) : SearchIntent

    sealed interface Photo : SearchIntent {
        data object Loading : Photo
        data object LoadComplete : Photo
        data object AppendLoading : Photo
        data object AppendLoadComplete : Photo
        data class OnError(val message: String?) : Photo
    }

    sealed interface Collection : SearchIntent {
        data object Loading : Collection
        data object LoadComplete : Collection
        data object AppendLoading : Collection
        data object AppendLoadComplete : Collection
        data class OnError(val message: String?) : Collection
    }

    sealed interface User : SearchIntent {
        data object Loading : User
        data object LoadComplete : User
        data object AppendLoading : User
        data object AppendLoadComplete : User
        data class OnError(val message: String?) : User
    }
}