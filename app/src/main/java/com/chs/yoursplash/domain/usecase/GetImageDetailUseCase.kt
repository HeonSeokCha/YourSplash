package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.UnSplashImageDetail
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageDetailUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    suspend operator fun invoke(photoId: String): Flow<Resource<UnSplashImageDetail>> {
        return repository.getSplashImageDetail(photoId)
    }
}