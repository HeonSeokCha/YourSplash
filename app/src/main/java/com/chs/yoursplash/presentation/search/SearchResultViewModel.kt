package com.chs.yoursplash.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultUserUseCase
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchResultPhotoUseCase: GetSearchResultPhotoUseCase,
    private val searchResultCollectionUseCase: GetSearchResultCollectionUseCase,
    private val searchResultUserUseCase: GetSearchResultUserUseCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {


    var state: SearchState by mutableStateOf(SearchState())

    init {
        getImageLoadQuality()
    }

    fun initSearchType(searchType: String) {
        state = state.copy(searchType = searchType)
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            state = state.copy(
                loadQuality = getLoadQualityUseCase()
            )
        }
    }

    fun searchResult(query: String) {
        state = when (state.searchType) {
            Constants.SEARCH_PHOTO -> {
                state.copy(
                    searchPhotoList = searchResultPhotoUseCase(
                        query = query,
                        orderBy = state.orderBy,
                        color = state.color,
                        orientation = state.orientation
                    ).cachedIn(viewModelScope)
                )
            }

            Constants.SEARCH_COLLECTION -> {
                state.copy(
                    searchCollectionList = searchResultCollectionUseCase(
                        query
                    ).cachedIn(viewModelScope)
                )
            }

            Constants.SEARCH_USER -> {
                state.copy(
                    searchUserList = searchResultUserUseCase(
                        query
                    ).cachedIn(viewModelScope)
                )
            }

            else -> state.copy()
        }
    }
}