package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import util.Constants
import com.chs.yoursplash.util.NetworkResult
import domain.usecase.GetImageDetailQualityUseCase
import domain.usecase.GetLoadQualityUseCase
import domain.usecase.GetPhotoDetailUseCase
import domain.usecase.GetPhotoRelatedListUseCase
import domain.usecase.GetPhotoSaveInfoUseCase
import domain.usecase.InsertPhotoSaveInfoUseCase
import kotlinx.coroutines.launch
import presentation.browse.photo_detail.PhotoDetailState

class PhotoDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val getPhotoRelatedListUseCase: GetPhotoRelatedListUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase,
    private val imageDetailQualityUseCase: GetImageDetailQualityUseCase,
    private val getPhotoSaveInfoUseCase: GetPhotoSaveInfoUseCase,
    private val insertPhotoSaveInfoUseCase: InsertPhotoSaveInfoUseCase
) : ViewModel() {

    private val imageId: String = savedStateHandle[Constants.ARG_KEY_PHOTO_ID] ?: ""

    var state by mutableStateOf(PhotoDetailState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                wallpaperQuality = imageDetailQualityUseCase(),
                loadQuality = loadQualityUseCase()
            )
        }
    }

    fun getImageDetailInfo() {
        viewModelScope.launch {
            getPhotoDetailUseCase(imageId).collect { result ->
                state = when (result) {
                    is NetworkResult.Loading -> {
                        state.copy(
                            isLoading = true,
                            isError = false
                        )
                    }

                    is NetworkResult.Success -> {
                        state.copy(
                            isLoading = false,
                            imageDetailInfo = result.data
                        )
                    }

                    is NetworkResult.Error -> {
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun getImageRelatedList() {
        viewModelScope.launch {
            getPhotoRelatedListUseCase(imageId).collect { result ->
                state = when (result) {
                    is NetworkResult.Loading -> {
                        state.copy(
                            isLoading = true,
                            isError = false
                        )
                    }

                    is NetworkResult.Success -> {
                        state.copy(
                            isLoading = false,
                            imageRelatedList = result.data ?: emptyList()
                        )
                    }

                    is NetworkResult.Error -> {
                        state.copy(
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