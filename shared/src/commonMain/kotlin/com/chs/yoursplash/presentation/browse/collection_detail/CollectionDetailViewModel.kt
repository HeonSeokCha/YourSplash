package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.ViewType
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetViewTypeUseCase
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailEffect.*
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CollectionDetailViewModel(
    private val collectionId: String,
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase,
    private val getViewTypeUseCase: GetViewTypeUseCase,
    getCollectionPhotoUseCase: GetCollectionPhotoUseCase
) : ViewModel() {

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
                _effect.trySend(ShowSnackBar(intent.message!!))
            }

            CollectionDetailIntent.ClickClose -> _effect.trySend(Close)
            is CollectionDetailIntent.ClickOpenBrowser -> {
                _effect.trySend(CollectionDetailEffect.NavigateBrowser(intent.id))
            }
        }
    }

    private fun getCollectionDetailInfo() {
        viewModelScope.launch {
            getViewTypeUseCase().collect { viewType ->
                _state.update { it.copy(isGrid = viewType == ViewType.Grid) }
            }
        }

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
                            _effect.trySend(ShowSnackBar(result.message!!))
                            it.copy(isDetailLoad = false)
                        }
                    }
                }
            }
        }
    }
}