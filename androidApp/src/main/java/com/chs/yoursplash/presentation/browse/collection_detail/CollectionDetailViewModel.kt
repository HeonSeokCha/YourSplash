package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
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