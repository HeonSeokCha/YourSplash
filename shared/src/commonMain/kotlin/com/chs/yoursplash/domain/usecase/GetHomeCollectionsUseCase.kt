package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetHomeCollectionsUseCase(
    private val repository: PhotoRepository,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) {
    operator fun invoke(): Flow<PagingData<UnSplashCollection>> = flow {
        emit(getLoadQualityUseCase().first())
    }.flatMapLatest {
        repository.getPagingCollection(it)
    }
}