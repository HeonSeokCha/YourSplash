package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionRelatedUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(id: String): Flow<Resource<List<UnSplashCollection>>> {
        return repository.getSplashCollectionRelated(id)
    }
}