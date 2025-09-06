package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetSearchResultCollectionUseCase(
    private val repository: SearchRepository,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) {
    operator fun invoke(query: String): Flow<PagingData<UnSplashCollection>> = flow {
        emit(getLoadQualityUseCase().first())
    }.flatMapLatest {
        repository.getSearchResultCollection(query = query, loadQuality = it)
    }
}