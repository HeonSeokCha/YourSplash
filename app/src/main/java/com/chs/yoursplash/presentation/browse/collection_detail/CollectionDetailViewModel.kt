package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionPhotoUserCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

    val state: StateFlow<CollectionDetailState> = flow {
        emit(CollectionDetailState(isLoading = true))
        emit(
            CollectionDetailState(
                isLoading = false,
                loadQuality = getLoadQualityUseCase(),
                collectionDetailInfo = getCollectionDetailUseCase(collectionId),
                collectionPhotos = getCollectionPhotoUserCase(collectionId)
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CollectionDetailState()
    )
}