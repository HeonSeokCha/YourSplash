package com.chs.yoursplash.presentation.search

sealed class SearchEvent {
    data object Idle : SearchEvent()
    data class ClickPhoto(val id: String) : SearchEvent()
    data class ClickCollection(val id: String) : SearchEvent()
    data class ClickUser(val name: String) : SearchEvent()
    data class TabIndex(val idx: Int) : SearchEvent()
    data class OnChangeSearchQuery(val query: String) : SearchEvent()
    data object OnError : SearchEvent()
}