package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SearchRepository
import javax.inject.Inject

class InsertSearchHistoryUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchHistory: String) {
        repository.insertSearchHistory(searchHistory)
    }
}
