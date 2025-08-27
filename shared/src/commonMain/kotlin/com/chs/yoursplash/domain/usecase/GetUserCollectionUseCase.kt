package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserCollectionUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userName: String): Flow<PagingData<UnSplashCollection>> {
        return repository.getUserDetailCollections(userName)
    }
}