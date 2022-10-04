package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoRelatedListUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    suspend operator fun invoke(id: String): Flow<Resource<List<Photo>>> {
        return repository.getSplashPhotoRelated(id)
    }
}