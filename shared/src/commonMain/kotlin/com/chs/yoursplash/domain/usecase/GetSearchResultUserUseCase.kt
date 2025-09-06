package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetSearchResultUserUseCase(
    private val repository: SearchRepository,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) {
    operator fun invoke(query: String): Flow<PagingData<User>> = flow {
        emit(getLoadQualityUseCase().first())
    }.flatMapLatest {
        repository.getSearchResultUser(query = query, loadQuality = it)
    }
}