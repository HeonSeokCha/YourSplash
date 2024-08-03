package domain.usecase

import androidx.paging.PagingData
import domain.model.Photo
import domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow

class GetCollectionPhotoUseCase(
    private val repository: PhotoRepository
) {
    operator fun invoke(id: String): Flow<PagingData<Photo>> {
        return repository.getPagingCollectionPhotos(id)
    }
}