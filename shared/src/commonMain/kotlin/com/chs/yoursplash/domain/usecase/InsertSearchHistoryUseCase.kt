package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SearchRepository
import org.koin.core.annotation.Single

@Single
class InsertSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchHistory: String) {
        repository.insertSearchHistory(searchHistory)
    }
}
