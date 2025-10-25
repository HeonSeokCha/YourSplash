package com.chs.yoursplash.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.usecase.GetSearchResultCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultUserUseCase
import com.chs.yoursplash.presentation.search.SearchEffect.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
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
    val state = _state.asStateFlow()
    private val queryState = MutableStateFlow("")
    private val searchFilterState = MutableStateFlow(SearchFilter())


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val photoPaging = combine(
        queryState,
        searchFilterState
    ) { query, filter ->
        query to filter
    }
        .filterNot { it.first.isEmpty() }
        .flatMapLatest { searchResultPhotoUseCase(it.first, it.second) }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val collectPaging = queryState
        .filterNot { it.isEmpty() }
        .flatMapLatest { searchResultCollectionUseCase(it) }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val userPaging = queryState
        .filterNot { it.isEmpty() }
        .flatMapLatest { searchResultUserUseCase(it) }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    private val _effect: Channel<SearchEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun changeEvent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.ChangeExpandColorFilter -> {
                _state.update { it.copy(expandColorFilter = intent.value) }
            }

            is SearchIntent.ChangeSearchFilter -> {
                _state.update { it.copy(searchFilter = intent.filter) }
                searchFilterState.update { intent.filter }
            }

            is SearchIntent.ChangeSearchQuery -> {
                queryState.update { intent.query }
            }

            is SearchIntent.ClickBrowseInfo -> {
                _effect.trySend(NavigateBrowse(intent.info))
            }

            is SearchIntent.ChangeShowModal -> _state.update { it.copy(showModal = intent.value) }
            is SearchIntent.ChangeTabIndex -> _state.update { it.copy(selectIdx = intent.idx) }

            SearchIntent.Collection.Loading -> _state.update { it.copy(isCollectionLoading = true) }
            SearchIntent.Collection.LoadComplete -> _state.update { it.copy(isCollectionLoading = false) }
            is SearchIntent.Collection.OnError -> _state.update { it.copy(collectionErrorMessage = intent.message) }

            SearchIntent.Photo.Loading -> _state.update { it.copy(isPhotoLoading = true) }
            SearchIntent.Photo.LoadComplete -> _state.update { it.copy(isPhotoLoading = false) }
            is SearchIntent.Photo.OnError -> _state.update { it.copy(photoErrorMessage = intent.message) }

            SearchIntent.User.Loading -> _state.update { it.copy(isUserLoading = true) }
            SearchIntent.User.LoadComplete -> _state.update { it.copy(isUserLoading = false) }
            is SearchIntent.User.OnError -> _state.update { it.copy(userErrorMessage = intent.message) }

            SearchIntent.Collection.AppendLoadComplete -> _state.update {
                it.copy(
                    isCollectAppendLoading = false
                )
            }

            SearchIntent.Collection.AppendLoading -> _state.update { it.copy(isCollectAppendLoading = true) }
            SearchIntent.Photo.AppendLoadComplete -> _state.update { it.copy(isPhotoAppendLoading = false) }
            SearchIntent.Photo.AppendLoading -> _state.update { it.copy(isPhotoAppendLoading = true) }
            SearchIntent.User.AppendLoadComplete -> _state.update { it.copy(isUserAppendLoading = false) }
            SearchIntent.User.AppendLoading -> _state.update { it.copy(isUserAppendLoading = true) }
        }
    }
}