package domain.usecase

import domain.repository.PhotoRepository
import com.chs.yoursplash.util.NetworkResult
import domain.model.UnSplashCollection
import kotlinx.coroutines.flow.Flow

class GetCollectionRelatedUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(id: String): Flow<NetworkResult<List<UnSplashCollection>>> {
        return repository.getRelatedCollectionList(id)
    }
}