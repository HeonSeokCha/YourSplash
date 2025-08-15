package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SearchRepository

class DeleteSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchHistory: String) {
        repository.deleteSearchHistory(searchHistory)
    }
}