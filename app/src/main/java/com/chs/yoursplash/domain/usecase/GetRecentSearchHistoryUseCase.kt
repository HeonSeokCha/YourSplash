package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentSearchHistoryUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getRecentSearchHistory()
    }
}