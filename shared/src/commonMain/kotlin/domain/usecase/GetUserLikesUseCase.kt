package domain.usecase

import androidx.paging.PagingData
import domain.model.Photo
import com.chs.yoursplash.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserLikesUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(userName: String): Flow<PagingData<Photo>> {
        return repository.getUserDetailLikePhotos(userName)
    }
}