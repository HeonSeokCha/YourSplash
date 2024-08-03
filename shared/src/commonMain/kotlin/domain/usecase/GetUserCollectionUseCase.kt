package domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.repository.UserRepository
import domain.model.UnSplashCollection
import kotlinx.coroutines.flow.Flow

class GetUserCollectionUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(userName: String): Flow<PagingData<UnSplashCollection>> {
        return repository.getUserDetailCollections(userName)
    }
}