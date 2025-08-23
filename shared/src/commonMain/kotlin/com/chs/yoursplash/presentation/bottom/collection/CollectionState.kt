package com.chs.yoursplash.presentation.bottom.collection

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import kotlinx.coroutines.flow.Flow

data class CollectionState(
    val collectionList: Flow<PagingData<UnSplashCollection>>? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
