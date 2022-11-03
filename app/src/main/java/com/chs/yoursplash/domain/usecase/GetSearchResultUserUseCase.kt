package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.repository.SplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultUserUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    operator fun invoke(query: String): Flow<PagingData<User>> {
        return repository.getSearchResultUser(query)
    }
}