package com.chs.yoursplash.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultUserUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

class SearchResultViewModel(
    private val searchResultPhotoUseCase: GetSearchResultPhotoUseCase,
    private val searchResultCollectionUseCase: GetSearchResultCollectionUseCase,
    private val searchResultUserUseCase: GetSearchResultUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SearchState()
        )
    private var searchJob: Job? = null

//    private val _event: Channel<SearchEvent> = Channel()
//    val event = _event.receiveAsFlow()

    fun changeEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnChangeSearchQuery -> {
                _state.update { it.copy(query = event.query) }
                searchResult(event.query)
            }
            is SearchEvent.TabIndex -> {
                _state.update {
                    it.copy(selectIdx = event.idx)
                }
            }

            SearchEvent.OnChangeShowModal -> {
                _state.update {
                    it.copy(isShowModal = !it.isShowModal)
                }
            }

            is SearchEvent.OnChangeSearchFilter -> {
                val currentState = _state.updateAndGet { it.copy(searchFilter = event.filter) }
                searchResult(currentState.query)
            }
            else -> Unit
        }
    }

    private fun searchResult(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    searchPhotoList = searchResultPhotoUseCase(
                        query = query,
                        searchFilter = it.searchFilter
                    ).cachedIn(viewModelScope),
                    searchCollectionList = searchResultCollectionUseCase(query).cachedIn(viewModelScope),
                    searchUserList = searchResultUserUseCase(query).cachedIn(viewModelScope)
                )
            }
        }
    }

    override fun onCleared() {
        searchJob?.cancel()
        super.onCleared()
    }
}