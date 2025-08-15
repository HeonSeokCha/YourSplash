package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetSearchResultUserUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<User>> {
        return repository.getSearchResultUser(query)
    }
}