package domain.usecase

import androidx.paging.PagingData
import domain.model.Photo
import domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserPhotoUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(userName: String): Flow<PagingData<Photo>> {
        return repository.getUserDetailPhotos(userName)
    }
}