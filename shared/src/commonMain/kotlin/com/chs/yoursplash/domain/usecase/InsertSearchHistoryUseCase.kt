package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SearchRepository

class InsertSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchHistory: String) {
        repository.insertSearchHistory(searchHistory)
    }
}
