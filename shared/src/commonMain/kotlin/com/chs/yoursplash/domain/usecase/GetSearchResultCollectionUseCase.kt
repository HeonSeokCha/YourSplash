package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetSearchResultCollectionUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<UnSplashCollection>> {
        return repository.getSearchResultCollection(query)
    }
}