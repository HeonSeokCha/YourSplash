package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase,
    private val getCollectionPhotoUseCase: GetCollectionPhotoUseCase,
) : ViewModel() {

    private val collectionId: String = savedStateHandle[Constants.ARG_KEY_COLLECTION_ID] ?: ""
    private var collectionDetailJob: Job? = null

    private val _state = MutableStateFlow(CollectionDetailState())
    val state = _state
        .onStart {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        collectionPhotos = getCollectionPhotoUseCase(collectionId).cachedIn(this)
                    )
                }
            }
            getCollectionDetailInfo()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private fun getCollectionDetailInfo() {
        collectionDetailJob?.cancel()
        collectionDetailJob = viewModelScope.launch {
            getCollectionDetailUseCase(collectionId).collect { result ->
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
                                collectionDetailInfo = result.data
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
        collectionDetailJob?.cancel()
        super.onCleared()
    }
}