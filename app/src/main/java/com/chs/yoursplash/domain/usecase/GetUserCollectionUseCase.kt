package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserCollectionUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userName: String): Flow<PagingData<UnSplashCollection>> {
        return repository.getUserDetailCollections(userName)
    }
}