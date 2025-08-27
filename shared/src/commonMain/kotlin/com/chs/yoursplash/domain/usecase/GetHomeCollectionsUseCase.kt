package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow

class GetHomeCollectionsUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(): Flow<PagingData<UnSplashCollection>> {
        return repository.getPagingCollection()
    }
}