package com.chs.yoursplash.presentation.search

import com.chs.yoursplash.domain.model.SearchFilter

sealed interface SearchIntent {
    data object ClickBack : SearchIntent
    data class ChangeTabIndex(val idx: Int) : SearchIntent
    data class ChangeSearchQuery(val query: String) : SearchIntent
    data class ChangeSearchFilter(val filter : SearchFilter) : SearchIntent
    data object ChangeShowModal : SearchIntent
    data object ChangeExpandColorFilter : SearchIntent

    sealed interface Photo : SearchIntent {
        data object Loading : Photo
        data object LoadComplete : Photo
        data class OnError(val message: String?) : Photo
        data class ClickPhoto(val id: String) : Photo
    }

    sealed interface Collection : SearchIntent {
        data object Loading : Collection
        data object LoadComplete : Collection
        data class OnError(val message: String?) : Collection
        data class ClickCollection(val id: String) : Collection
    }

    sealed interface User : SearchIntent {
        data object Loading : User
        data object LoadComplete : User
        data class OnError(val message: String?) : User
        data class ClickUser(val userName: String) : User
    }
}