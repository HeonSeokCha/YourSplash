package com.chs.yoursplash.presentation.bottom.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomePhotosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomePhotosUseCase: GetHomePhotosUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            getPhoto()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            HomeState()
        )

    fun changeEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnRefresh -> {
                _state.update {
                    it.copy(isRefresh = true)
                }
                getPhoto()
            }
            else -> Unit
        }
    }

    private fun getPhoto() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRefresh = false,
                    isLoading = false,
                    pagingImageList = getHomePhotosUseCase().cachedIn(viewModelScope)
                )
            }
        }
    }
}