package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.*
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
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
                        state.copy(isLoading = true)
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
                            isError = result.message
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
                        state.copy(isLoading = true)
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
                            isError = result.message
                        )
                    }
                }
            }
        }
    }
}