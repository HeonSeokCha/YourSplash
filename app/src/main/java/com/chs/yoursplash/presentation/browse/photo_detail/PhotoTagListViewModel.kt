package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetStringPrefUseCase
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoTagListViewModel @Inject constructor(
    private val getSearchResultPhotoUseCase: GetSearchResultPhotoUseCase,
    private val getStringPrefUseCase: GetStringPrefUseCase
) : ViewModel() {

    var state by mutableStateOf(PhotoTagListState())
        private set

    init {
        getImageLoadQuality()
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            state = state.copy(
                loadQuality = getStringPrefUseCase(
                    Constants.PREFERENCE_KEY_LOAD_QUALITY
                ).first().ifEmpty { "regular" }
            )
        }
    }

    fun getTagSearchResult(tag: String) {
        state = state.copy(
            tagSearchResultList = getSearchResultPhotoUseCase(
                query = tag,
                orderBy = "relevant",
                color = null,
                orientation = null
            ).cachedIn(viewModelScope)
        )
    }
}