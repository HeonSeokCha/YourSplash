package com.chs.yoursplash.presentation.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.usecase.GetHomePhotosUseCase
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePhotosUseCase: GetHomePhotosUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        getImageList()
    }

    fun getImageList(): Flow<PagingData<Photo>> {
        return getHomePhotosUseCase().cachedIn(viewModelScope)
    }
}