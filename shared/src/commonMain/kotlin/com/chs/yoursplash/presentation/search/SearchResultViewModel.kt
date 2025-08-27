package com.chs.yoursplash.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchResultViewModel(
    private val searchResultPhotoUseCase: GetSearchResultPhotoUseCase,
    private val searchResultCollectionUseCase: GetSearchResultCollectionUseCase,
    private val searchResultUserUseCase: GetSearchResultUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .onStart {
//            getImageLoadQuality()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SearchState()
        )

    private val _event: Channel<SearchEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun changeEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnChangeSearchQuery -> {
                _state.update { it.copy() }
            }
            SearchEvent.OnError -> {

            }
            is SearchEvent.TabIndex -> {

            }
            else -> Unit
        }
    }

    fun searchResult() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    searchPhotoList = searchResultPhotoUseCase(it.searchFilter).cachedIn(viewModelScope),
                    searchCollectionList = searchResultCollectionUseCase(it.searchFilter.query).cachedIn(viewModelScope),
                    searchUserList = searchResultUserUseCase(it.searchFilter.query).cachedIn(viewModelScope)
                )
            }
        }
    }
}