package com.chs.yoursplash.presentation.main.collection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase
) : ViewModel() {

    var state by mutableStateOf(CollectionState())
        private set

    init {
        getHomeCollections()
    }

    private fun getHomeCollections() {
        state = state.copy(
            collectionList = getHomeCollectionsUseCase().cachedIn(viewModelScope)
        )
    }
}