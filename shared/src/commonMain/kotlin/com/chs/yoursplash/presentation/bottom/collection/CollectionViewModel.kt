package com.chs.yoursplash.presentation.bottom.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CollectionViewModel(
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CollectionState())
    val state = _state.onStart {
        _state.update {
            it.copy(
                isLoading = false,
                collectionList = getHomeCollectionsUseCase().cachedIn(viewModelScope)
            )
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CollectionState()
        )
}