package com.chs.yoursplash.presentation.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    val state: StateFlow<CollectionState> = flow {
        emit(CollectionState(isLoading = true))
        emit(
            CollectionState(
                isLoading = false,
                loadQuality = loadQualityUseCase(),
                collectionList = getHomeCollectionsUseCase().cachedIn(viewModelScope)
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CollectionState()
    )
}