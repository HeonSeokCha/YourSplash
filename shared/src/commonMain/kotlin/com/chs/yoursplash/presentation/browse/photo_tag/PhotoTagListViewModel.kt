package com.chs.yoursplash.presentation.browse.photo_tag

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhotoTagListViewModel(
    savedStateHandle: SavedStateHandle,
    getSearchResultPhotoUseCase: GetSearchResultPhotoUseCase,
) : ViewModel() {

    private val tagName: String = savedStateHandle[Constants.ARG_KEY_TAG_NAME] ?: ""
    val pagingItems: Flow<PagingData<Photo>> = getSearchResultPhotoUseCase(
        query = tagName,
        searchFilter = SearchFilter()
    )
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(PhotoTagListState())
    val state = _state.asStateFlow()
}