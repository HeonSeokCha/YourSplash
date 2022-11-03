package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultCollectionUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    operator fun invoke(query: String): Flow<PagingData<UnSplashCollection>> {
        return repository.getSearchResultCollection(query)
    }
}