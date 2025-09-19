package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.presentation.bottom.collection.CollectionEffect
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailEffect.*
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase,
    getCollectionPhotoUseCase: GetCollectionPhotoUseCase,
) : ViewModel() {

    private val collectionId: String = savedStateHandle[Constants.ARG_KEY_COLLECTION_ID] ?: ""
    val pagingItems: Flow<PagingData<Photo>> =
        getCollectionPhotoUseCase(id = collectionId).cachedIn(viewModelScope)

    private val _state = MutableStateFlow(CollectionDetailState())
    val state = _state
        .onStart {
            getCollectionDetailInfo()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _effect = Channel<CollectionDetailEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun changeIntent(intent: CollectionDetailIntent) {
        when (intent) {
            CollectionDetailIntent.LoadComplete -> _state.update { it.copy(isPagingLoading = false) }
            CollectionDetailIntent.Loading -> _state.update { it.copy(isPagingLoading = true) }
            is CollectionDetailIntent.ClickPhoto -> {
                _effect.trySend(
                    NavigatePhotoDetail(intent.id)
                )
            }

            is CollectionDetailIntent.ClickUser -> {
                _effect.trySend(
                    NavigateUserDetail(intent.name)
                )
            }

            is CollectionDetailIntent.OnError -> {
                _effect.trySend(ShowToast(intent.message!!))
            }

            CollectionDetailIntent.ClickClose -> _effect.trySend(Close)
        }
    }

    private fun getCollectionDetailInfo() {
        viewModelScope.launch {
            getCollectionDetailUseCase(collectionId).collect { result ->
                _state.update {
                    when (result) {
                        is NetworkResult.Loading -> {
                            it.copy(isDetailLoad = true)
                        }

                        is NetworkResult.Success -> {
                            it.copy(
                                isDetailLoad = false,
                                collectionDetailInfo = result.data
                            )
                        }

                        is NetworkResult.Error -> {
                            _effect.trySend(ShowToast(result.message!!))
                            it.copy(isDetailLoad = false)
                        }
                    }
                }
            }
        }
    }
}