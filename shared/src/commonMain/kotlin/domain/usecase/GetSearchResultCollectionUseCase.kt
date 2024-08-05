package domain.usecase

import androidx.paging.PagingData
import domain.repository.SearchRepository
import domain.model.UnSplashCollection
import kotlinx.coroutines.flow.Flow

class GetSearchResultCollectionUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<UnSplashCollection>> {
        return repository.getSearchResultCollection(query)
    }
}