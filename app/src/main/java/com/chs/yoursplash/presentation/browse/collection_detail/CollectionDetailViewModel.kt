package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionPhotoUserCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase,
    private val getCollectionPhotoUserCase: GetCollectionPhotoUserCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<CollectionDetailState> =
        MutableStateFlow(CollectionDetailState())
    val state: StateFlow<CollectionDetailState> = _state.asStateFlow()

    init {
        getImageLoadQuality()
    }

    fun getCollectionDetail(id: String) {
        viewModelScope.launch {
            getCollectionDetailUseCase(id).collect { result ->
                _state.update {
                    when (result) {
                        is Resource.Loading -> {
                            it.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            it.copy(
                                isLoading = false,
                                collectionDetailInfo = result.data
                            )
                        }

                        is Resource.Error -> {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                    }
                }
            }
        }

    }

    fun getCollectionPhotos(id: String) {
        _state.update {
            it.copy(
                collectionPhotos = getCollectionPhotoUserCase(id).cachedIn(viewModelScope)
            )
        }
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loadQuality = getLoadQualityUseCase()
                )
            }
        }
    }
}