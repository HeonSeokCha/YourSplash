package com.chs.yoursplash.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetSearchResultCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultUserUseCase
import com.chs.yoursplash.domain.usecase.GetStringPrefUseCase
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchResultPhotoUseCase: GetSearchResultPhotoUseCase,
    private val searchResultCollectionUseCase: GetSearchResultCollectionUseCase,
    private val searchResultUserUseCase: GetSearchResultUserUseCase,
    private val getStringPrefUseCase: GetStringPrefUseCase
) : ViewModel() {

    var searchPage: String = ""
    var orderBy: String = "relevant"
    var color: String? = null
    var orientation: String? = null
    var state by mutableStateOf(SearchState())

    init {
        getImageLoadQuality()
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            state = state.copy(
                loadQuality = getStringPrefUseCase(Constants.PREFERENCE_KEY_LOAD_QUALITY).first()
            )
        }
    }

    fun searchResult(query: String) {
        when (searchPage) {
            Constants.SEARCH_PHOTO -> {
                state = state.copy(
                    searchPhotoList = searchResultPhotoUseCase(
                        query = query,
                        orderBy = orderBy,
                        color = color,
                        orientation = orientation
                    ).cachedIn(viewModelScope)
                )
            }
            Constants.SEARCH_COLLECTION -> {
                state = state.copy(
                    searchCollectionList = searchResultCollectionUseCase(
                        query
                    ).cachedIn(viewModelScope)
                )
            }
            Constants.SEARCH_USER -> {
                state = state.copy(
                    searchUserList = searchResultUserUseCase(
                        query
                    ).cachedIn(viewModelScope)
                )
            }
        }
    }
}