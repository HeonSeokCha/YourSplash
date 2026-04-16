package com.chs.yoursplash.presentation.bottom.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.ViewType
import com.chs.yoursplash.domain.usecase.GetHomePhotosUseCase
import com.chs.yoursplash.domain.usecase.GetViewTypeUseCase
import com.chs.yoursplash.presentation.bottom.photo.PhotoEffect.*
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
class PhotoViewModel(
    getHomePhotosUseCase: GetHomePhotosUseCase,
    private val getViewTypeUseCase: GetViewTypeUseCase
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<Photo>> = getHomePhotosUseCase()
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(PhotoState())
    val state = _state
        .onStart {
            observeViewType()
        }
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

    private fun observeViewType() {
        viewModelScope.launch {
            getViewTypeUseCase().collect { viewType ->
                _state.update { it.copy(isGrid = viewType == ViewType.Grid) }
            }
        }
    }
}