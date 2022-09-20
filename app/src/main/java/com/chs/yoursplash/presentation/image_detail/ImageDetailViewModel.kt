package com.chs.yoursplash.presentation.image_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetImageDetailUseCase
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val getImageDetailUseCase: GetImageDetailUseCase
) : ViewModel() {

    var state by mutableStateOf(ImageDetailState())

    fun getImageDetailInfo(imageId: String) {
        viewModelScope.launch {
            getImageDetailUseCase(imageId).collect { result ->
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
}