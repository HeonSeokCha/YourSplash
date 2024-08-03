package domain.usecase

import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetRecentSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getRecentSearchHistory()
    }
}