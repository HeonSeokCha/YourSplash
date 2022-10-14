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
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase,
    private val getCollectionPhotoUserCase: GetCollectionPhotoUserCase
) : ViewModel() {

    var state by mutableStateOf(CollectionDetailState())

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

    fun getCollectionPhotos(id: String): Flow<PagingData<Photo>> {
        return getCollectionPhotoUserCase(id).cachedIn(viewModelScope)
    }
}