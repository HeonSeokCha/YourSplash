package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeCollectionsUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    operator fun invoke(): Flow<PagingData<UnSplashCollection>> {
        return repository.getSplashCollection()
    }
}