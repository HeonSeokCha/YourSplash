package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import domain.usecase.GetCollectionDetailUseCase
import domain.usecase.GetCollectionPhotoUseCase
import domain.usecase.GetLoadQualityUseCase
import util.Constants
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.launch
import presentation.browse.collection_detail.CollectionDetailState

class CollectionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase,
    private val getCollectionPhotoUseCase: GetCollectionPhotoUseCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    private val collectionId: String = savedStateHandle[Constants.ARG_KEY_COLLECTION_ID] ?: ""

    var state by mutableStateOf(CollectionDetailState())
        private set

    init {
        viewModelScope.launch {
            state = CollectionDetailState(
                loadQuality = getLoadQualityUseCase(),
                collectionPhotos = getCollectionPhotoUseCase(collectionId).cachedIn(this)
            )
        }
    }

    fun getCollectionDetailInfo() {
        viewModelScope.launch {
            getCollectionDetailUseCase(collectionId).collect { result ->
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
                            collectionDetailInfo = result.data
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