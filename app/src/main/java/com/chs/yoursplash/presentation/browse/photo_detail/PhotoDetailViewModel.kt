package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.*
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val getPhotoRelatedListUseCase: GetPhotoRelatedListUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase,
    private val imageDetailQualityUseCase: GetImageDetailQualityUseCase,
    private val getPhotoSaveInfoUseCase: GetPhotoSaveInfoUseCase,
    private val insertPhotoSaveInfoUseCase: InsertPhotoSaveInfoUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<PhotoDetailState> = MutableStateFlow(PhotoDetailState())
    val state: StateFlow<PhotoDetailState> = _state.asStateFlow()


    init {
        getImageLoadQuality()
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    wallpaperQuality = imageDetailQualityUseCase(),
                    loadQuality = loadQualityUseCase()
                )
            }
        }
    }

    fun getImageDetailInfo(imageId: String) {
        viewModelScope.launch {
            getPhotoDetailUseCase(imageId).collect { result ->
                _state.update {
                    when (result) {
                        is Resource.Loading -> {
                            it.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            it.copy(
                                isLoading = false,
                                imageDetailInfo = result.data,
                                downloadFileName = "${result.data?.user?.userName}-${result.data?.id}.jpg"
                            )
                        }

                        is Resource.Error -> {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun getImageRelatedList(imageId: String) {
        viewModelScope.launch {
            getPhotoRelatedListUseCase(imageId).collect { result ->
                _state.update {
                    when (result) {
                        is Resource.Loading -> {
                            it.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            it.copy(
                                isLoading = false,
                                imageRelatedList = result.data!!
                            )
                        }

                        is Resource.Error -> {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }
                }
            }
        }
    }
}