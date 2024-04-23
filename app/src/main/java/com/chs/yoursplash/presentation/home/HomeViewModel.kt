package com.chs.yoursplash.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomePhotosUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePhotosUseCase: GetHomePhotosUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    val state: StateFlow<HomeState> = flow {
        emit(HomeState(isLoading = true))
        emit(
            HomeState(
                isLoading = false,
                loadQuality = loadQualityUseCase(),
                pagingImageList = getHomePhotosUseCase().cachedIn(viewModelScope)
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        HomeState()
    )
}