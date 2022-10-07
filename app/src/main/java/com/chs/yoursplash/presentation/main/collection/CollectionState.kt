package com.chs.yoursplash.presentation.main.collection

import com.chs.yoursplash.domain.model.UnSplashCollection
import javax.annotation.concurrent.Immutable

@Immutable
data class CollectionState(
    val collectionList: List<UnSplashCollection> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
