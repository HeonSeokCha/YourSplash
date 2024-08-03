package domain.usecase

import domain.repository.PhotoRepository
import com.chs.yoursplash.util.NetworkResult
import domain.model.UnSplashCollection
import kotlinx.coroutines.flow.Flow

class GetCollectionDetailUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(id: String): Flow<NetworkResult<UnSplashCollection>> {
        return repository.getCollectionDetailInfo(id)
    }
}