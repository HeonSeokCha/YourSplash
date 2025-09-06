package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.flow.Flow

class GetCollectionDetailUseCase(
    private val repository: PhotoRepository
) {
    operator fun invoke(id: String): Flow<NetworkResult<UnSplashCollection>> {
        return repository.getCollectionDetailInfo(id = id)
    }
}