package com.chs.yoursplash.presentation.collection

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import kotlinx.coroutines.flow.Flow
import javax.annotation.concurrent.Immutable

@Immutable
data class CollectionState(
    val collectionList: Flow<PagingData<UnSplashCollection>>? = null,
    val loadQuality: String = "regular",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
