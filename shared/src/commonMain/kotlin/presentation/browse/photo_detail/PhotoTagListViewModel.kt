package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import domain.usecase.GetLoadQualityUseCase
import domain.usecase.GetSearchResultPhotoUseCase
import util.Constants
import kotlinx.coroutines.launch

class PhotoTagListViewModel(
    savedStateHandle: SavedStateHandle,
    private val getSearchResultPhotoUseCase: GetSearchResultPhotoUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    private val tagName: String = savedStateHandle[Constants.ARG_KEY_TAG_NAME] ?: ""

    var state by mutableStateOf(PhotoTagListState())
        private set

    init {
        viewModelScope.launch {
            state = PhotoTagListState(
                isLoading = false,
                loadQuality = loadQualityUseCase(),
                tagSearchResultList = getSearchResultPhotoUseCase(
                    query = tagName,
                    orderBy = "relevant",
                    color = null,
                    orientation = null
                ).cachedIn(viewModelScope)
            )
        }
    }
}