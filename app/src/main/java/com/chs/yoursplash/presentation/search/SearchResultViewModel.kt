package com.chs.yoursplash.presentation.search

import android.util.Log
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

    private var _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    init {
        Log.e("CHS_LOG", "init" + _state.value.searchType.toString())
        getImageLoadQuality()
    }

    fun initSearchType(searchType: String) {
        _state.update {
            it.copy(
                searchType = searchType
            )
        }
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loadQuality = getLoadQualityUseCase()
                )
            }
        }
    }

    fun searchResult(query: String) {
        _state.update {
            when (it.searchType) {
                Constants.SEARCH_PHOTO -> {
                    it.copy(
                        searchPhotoList = searchResultPhotoUseCase(
                            query = query,
                            orderBy = it.orderBy,
                            color = it.color,
                            orientation = it.orientation
                        ).cachedIn(viewModelScope)
                    )
                }
                Constants.SEARCH_COLLECTION -> {
                    it.copy(
                        searchCollectionList = searchResultCollectionUseCase(
                            query
                        ).cachedIn(viewModelScope)
                    )
                }
                Constants.SEARCH_USER -> {
                    it.copy(
                        searchUserList = searchResultUserUseCase(
                            query
                        ).cachedIn(viewModelScope)
                    )
                }
                else -> {
                    it
                }
            }
        }
    }
}