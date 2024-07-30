package com.chs.yoursplash.presentation.bottom.collection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    var state by mutableStateOf(CollectionState())
        private set

    init {
        viewModelScope.launch {
            state = CollectionState(
                isLoading = false,
                loadQuality = loadQualityUseCase(),
                collectionList = getHomeCollectionsUseCase().cachedIn(this)
            )
        }
    }
}