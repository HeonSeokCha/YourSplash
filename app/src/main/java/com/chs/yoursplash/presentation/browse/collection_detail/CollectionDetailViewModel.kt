package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionPhotoUserCase
import com.chs.yoursplash.domain.usecase.GetStringPrefUseCase
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase,
    private val getCollectionPhotoUserCase: GetCollectionPhotoUserCase,
    private val getStringPrefUseCase: GetStringPrefUseCase
) : ViewModel() {

    var state by mutableStateOf(CollectionDetailState())

    init {
        getImageLoadQuality()
    }

    fun getCollectionDetail(id: String) {
        viewModelScope.launch {
            getCollectionDetailUseCase(id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            collectionDetailInfo = result.data
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    fun getCollectionPhotos(id: String) {
        state = state.copy(
            collectionPhotos = getCollectionPhotoUserCase(id).cachedIn(viewModelScope)
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