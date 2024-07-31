package com.chs.yoursplash.presentation.browse.collection_detail

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import kotlinx.coroutines.flow.Flow

data class CollectionDetailState(
    val collectionDetailInfo: UnSplashCollection? = null,
    val collectionPhotos: Flow<PagingData<Photo>>? = null,
    val loadQuality: String = "Regular",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
