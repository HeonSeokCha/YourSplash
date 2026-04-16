package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetRecentSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getRecentSearchHistory()
    }
}