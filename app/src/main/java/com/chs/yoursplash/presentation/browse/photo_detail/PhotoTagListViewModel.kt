package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoTagListViewModel @Inject constructor(
    private val getSearchResultPhotoUseCase: GetSearchResultPhotoUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<PhotoTagListState> = MutableStateFlow(PhotoTagListState())
    val state: StateFlow<PhotoTagListState> = _state.asStateFlow()

    init {
        getImageLoadQuality()
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loadQuality = loadQualityUseCase()
                )
            }
        }
    }

    fun getTagSearchResult(tag: String) {
        _state.update {
            it.copy(
                tagSearchResultList = getSearchResultPhotoUseCase(
                    query = tag,
                    orderBy = "relevant",
                    color = null,
                    orientation = null
                ).cachedIn(viewModelScope)
            )
        }
    }
}