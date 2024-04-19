package com.chs.yoursplash.presentation.main.collection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase,
    private val getStringPrefUseCase: GetStringPrefUseCase
) : ViewModel() {

    var state by mutableStateOf(CollectionState())
        private set

    init {
        getHomeCollections()
        getImageLoadQuality()
    }

    private fun getHomeCollections() {
        state = state.copy(
            collectionList = getHomeCollectionsUseCase().cachedIn(viewModelScope)
        )
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            state = state.copy(
                loadQuality = getStringPrefUseCase(Constants.PREFERENCE_KEY_LOAD_QUALITY).first()
            )
        }
    }
}