package com.chs.yoursplash.presentation.bottom.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.usecase.GetHomePhotosUseCase
import com.chs.yoursplash.presentation.ErrorState
import com.chs.yoursplash.presentation.LoadingState
import com.chs.yoursplash.presentation.bottom.home.HomeEffect.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    getHomePhotosUseCase: GetHomePhotosUseCase,
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<Photo>> = getHomePhotosUseCase()
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Loading -> updateState { it.copy(loadingState = LoadingState.Loading) }

            is HomeIntent.ClickPhoto -> {
                _effect.trySend(NavigatePhotoDetail(intent.id))
            }

            is HomeIntent.ClickUser -> {
                _effect.trySend(NavigateUserDetail(intent.name))
            }

            HomeIntent.LoadComplete -> {
                updateState {
                    it.copy(
                        isRefresh = false,
                        loadingState = LoadingState.Success
                    )
                }
            }

            HomeIntent.RefreshData -> {
                updateState {
                    it.copy(
                        isRefresh = true,
                        loadingState = LoadingState.Loading
                    )
                }
            }

            is HomeIntent.OnError -> {
            }
        }
    }

    private fun updateState(reducer: (HomeState) -> HomeState) {
        _state.value = reducer(_state.value)
    }
}