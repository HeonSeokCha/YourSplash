package domain.usecase

import domain.repository.SearchRepository

class InsertSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchHistory: String) {
        repository.insertSearchHistory(searchHistory)
    }
}
