package com.chs.yoursplash.presentation.bottom.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomePhotosUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePhotosUseCase: GetHomePhotosUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            state = HomeState(
                isLoading = false,
                loadQuality = loadQualityUseCase(),
                pagingImageList = getHomePhotosUseCase().cachedIn(viewModelScope)
            )
        }
    }
}