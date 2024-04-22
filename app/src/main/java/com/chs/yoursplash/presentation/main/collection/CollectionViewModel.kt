package com.chs.yoursplash.presentation.main.collection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<CollectionState> = MutableStateFlow(CollectionState())
    val state: StateFlow<CollectionState> = _state.asStateFlow()

    init {
        getHomeCollections()
        getImageLoadQuality()
    }

    private fun getHomeCollections() {
        _state.update {
            it.copy(
                collectionList = getHomeCollectionsUseCase().cachedIn(viewModelScope)
            )
        }
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loadQuality = loadQualityUseCase()
                )
            }
        }
    }
}