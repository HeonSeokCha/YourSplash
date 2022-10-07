package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.launch

class CollectionDetailViewModel(
    private val getCollectionDetailUseCase: GetCollectionDetailUseCase
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
}