package com.chs.yoursplash.presentation.browse.image_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetPhotoDetailUseCase
import com.chs.yoursplash.domain.usecase.GetPhotoRelatedListUseCase
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val getPhotoRelatedListUseCase: GetPhotoRelatedListUseCase
) : ViewModel() {

    var state by mutableStateOf(ImageDetailState())

    fun getImageDetailInfo(imageId: String) {
        viewModelScope.launch {
            getPhotoDetailUseCase(imageId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            imageDetailInfo = result.data
                        )
                    }
                    is Resource.Error -> {

                        state = state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    fun getImageRelatedList(imageId: String) {
        viewModelScope.launch {
            getPhotoRelatedListUseCase(imageId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            imageRelatedList = result.data!!
                        )
                    }
                    is Resource.Error -> {

                        state = state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }
}