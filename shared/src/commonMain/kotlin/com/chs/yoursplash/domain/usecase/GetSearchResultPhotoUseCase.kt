package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetSearchResultPhotoUseCase(
    private val repository: SearchRepository,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) {
    operator fun invoke(
        query: String,
        searchFilter: SearchFilter
    ): Flow<PagingData<Photo>> = flow {
        emit(getLoadQualityUseCase().first())
    }.flatMapLatest {
        repository.getSearchResultPhoto(
            query = query,
            searchFilter = searchFilter,
            loadQuality = it
        )
    }
}