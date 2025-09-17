package com.chs.yoursplash.presentation.bottom.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.usecase.GetHomePhotosUseCase
import com.chs.yoursplash.presentation.bottom.photo.PhotoEffect.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class PhotoViewModel(
    getHomePhotosUseCase: GetHomePhotosUseCase,
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<Photo>> = getHomePhotosUseCase()
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(PhotoState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _effect = Channel<PhotoEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: PhotoIntent) {
        when (intent) {
            PhotoIntent.Loading -> updateState { it.copy(isLoading = true) }

            is PhotoIntent.ClickPhoto -> {
                _effect.trySend(NavigatePhotoDetail(intent.id))
            }

            is PhotoIntent.ClickUser -> {
                _effect.trySend(NavigateUserDetail(intent.name))
            }

            PhotoIntent.LoadComplete -> {
                updateState {
                    it.copy(
                        isRefresh = false,
                        isLoading = false
                    )
                }
            }

            PhotoIntent.RefreshData -> {
                updateState {
                    it.copy(isRefresh = true)
                }
            }

            is PhotoIntent.OnError -> {
            }
        }
    }

    private fun updateState(reducer: (PhotoState) -> PhotoState) {
        _state.value = reducer(_state.value)
    }
}