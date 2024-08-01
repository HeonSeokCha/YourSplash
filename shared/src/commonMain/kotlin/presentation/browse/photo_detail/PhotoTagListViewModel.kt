package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoTagListViewModel @Inject constructor(
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