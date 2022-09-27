package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageRelatedListUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    suspend operator fun invoke(id: String): Flow<Resource<List<UnSplashImage>>> {
        return repository.getSplashImageRelated(id)
    }
}