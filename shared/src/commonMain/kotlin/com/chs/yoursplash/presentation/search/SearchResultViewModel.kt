package com.chs.yoursplash.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetSearchResultCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultUserUseCase
import com.chs.yoursplash.presentation.LoadingState
import com.chs.yoursplash.presentation.search.SearchEffect.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
            _state.value
        )
    private var searchJob: Job? = null

    private val _effect: Channel<SearchEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun changeEvent(intent: SearchIntent) {
        when (intent) {
            SearchIntent.ChangeExpandColorFilter -> {
                _state.update { it.copy(expandColorFilter = !it.expandColorFilter) }
            }
            is SearchIntent.ChangeSearchFilter -> {
                _state.update {
                    it.copy(
                        searchPhotoList =searchResultPhotoUseCase(
                            query = it.query,
                            searchFilter = intent.filter
                        ).cachedIn(viewModelScope),
                        searchFilter = intent.filter
                    )
                }
            }

            is SearchIntent.ClickBrowseInfo -> {
                _effect.trySend(NavigateBrowse(intent.info))
            }

            is SearchIntent.ChangeSearchQuery -> {
                if (intent.query.isEmpty()) {
                    _state.update { SearchState() }
                    return
                }

                _state.update { it.copy(query = intent.query) }
                searchResult(intent.query)
            }
            is SearchIntent.ChangeShowModal -> _state.update { it.copy(showModal = !it.showModal) }
            is SearchIntent.ChangeTabIndex -> _state.update { it.copy(selectIdx = intent.idx) }

            SearchIntent.Collection.Loading -> _state.update { it.copy(collectionLoadingState = LoadingState.Loading) }
            SearchIntent.Collection.LoadComplete -> _state.update { it.copy(collectionLoadingState = LoadingState.Success) }
            is SearchIntent.Collection.OnError -> _state.update { it.copy(collectionErrorMessage = intent.message) }

            SearchIntent.Photo.Loading -> _state.update { it.copy(photoLoadingState = LoadingState.Loading) }
            SearchIntent.Photo.LoadComplete -> _state.update { it.copy(photoLoadingState = LoadingState.Success) }
            is SearchIntent.Photo.OnError -> _state.update { it.copy(photoErrorMessage = intent.message) }

            SearchIntent.User.Loading -> _state.update { it.copy(userLoadingState = LoadingState.Loading) }
            SearchIntent.User.LoadComplete -> _state.update { it.copy(userLoadingState = LoadingState.Success) }
            is SearchIntent.User.OnError -> _state.update { it.copy(userErrorMessage = intent.message) }
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
                    searchCollectionList = searchResultCollectionUseCase(query).cachedIn(
                        viewModelScope
                    ),
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