package com.chs.yoursplash.presentation.bottom.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomePhotosUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val getHomePhotosUseCase: GetHomePhotosUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeEvent())
    val state = _state
        .onStart {
            _state.update {
                it.copy(
                    isLoading = false,
                    pagingImageList = getHomePhotosUseCase().cachedIn(viewModelScope)
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            HomeEvent()
        )
}