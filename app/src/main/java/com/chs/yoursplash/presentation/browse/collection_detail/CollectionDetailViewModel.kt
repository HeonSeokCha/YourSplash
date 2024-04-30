package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionPhotoUserCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase,
    private val getCollectionPhotoUserCase: GetCollectionPhotoUserCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    private val collectionId: String = savedStateHandle[Constants.ARG_KEY_COLLECTION_ID] ?: ""

    var state by mutableStateOf(CollectionDetailState())
        private set

    init {
        viewModelScope.launch {
            state = CollectionDetailState(
                isLoading = false,
                loadQuality = getLoadQualityUseCase(),
                collectionDetailInfo = getCollectionDetailUseCase(collectionId),
                collectionPhotos = getCollectionPhotoUserCase(collectionId).cachedIn(this)
            )
        }
    }
}