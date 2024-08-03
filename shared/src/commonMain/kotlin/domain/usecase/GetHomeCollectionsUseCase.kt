package domain.usecase

import androidx.paging.PagingData
import domain.model.UnSplashCollection
import domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow

class GetHomeCollectionsUseCase(
    private val repository: PhotoRepository
) {
    operator fun invoke(): Flow<PagingData<UnSplashCollection>> {
        return repository.getPagingCollection()
    }
}