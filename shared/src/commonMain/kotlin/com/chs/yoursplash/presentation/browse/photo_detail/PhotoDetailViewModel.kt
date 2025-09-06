package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetImageDetailQualityUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetPhotoDetailUseCase
import com.chs.yoursplash.domain.usecase.GetPhotoRelatedListUseCase
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val getPhotoRelatedListUseCase: GetPhotoRelatedListUseCase,

) : ViewModel() {

    private val imageId: String = savedStateHandle[Constants.ARG_KEY_PHOTO_ID] ?: ""
    private var imageDetailJob: Job? = null
    private var relatedListJob: Job? = null
    private var _state = MutableStateFlow(PhotoDetailState())
    val state = _state
        .onStart {
            getImageDetailInfo()
            getImageRelatedList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private fun getImageDetailInfo() {
        imageDetailJob?.cancel()
        imageDetailJob = viewModelScope.launch {
            getPhotoDetailUseCase(imageId).collect { result ->
                _state.update {
                    when (result) {
                        is NetworkResult.Loading -> {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }

                        is NetworkResult.Success -> {
                            it.copy(
                                isLoading = false,
                                imageDetailInfo = result.data
                            )
                        }

                        is NetworkResult.Error -> {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getImageRelatedList() {
        relatedListJob?.cancel()
        relatedListJob = viewModelScope.launch {
            getPhotoRelatedListUseCase(imageId).collect { result ->
                _state.update {
                    when (result) {
                        is NetworkResult.Loading -> {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }

                        is NetworkResult.Success -> {
                            it.copy(
                                isLoading = false,
                                imageRelatedList = result.data ?: emptyList()
                            )
                        }

                        is NetworkResult.Error -> {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        imageDetailJob?.cancel()
        relatedListJob?.cancel()
        super.onCleared()
    }
}