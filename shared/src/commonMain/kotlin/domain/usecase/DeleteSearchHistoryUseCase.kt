package domain.usecase

import domain.repository.SearchRepository

class DeleteSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(searchHistory: String) {
        repository.deleteSearchHistory(searchHistory)
    }
}