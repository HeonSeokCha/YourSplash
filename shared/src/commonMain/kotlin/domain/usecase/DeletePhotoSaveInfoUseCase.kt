package domain.usecase

import domain.model.PhotoSaveInfo
import domain.repository.PhotoRepository

class DeletePhotoSaveInfoUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photoSaveInfo: PhotoSaveInfo) {
        return repository.deletePhotoSaveInfo(photoSaveInfo)
    }
}