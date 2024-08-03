package domain.usecase

import domain.model.PhotoSaveInfo
import domain.repository.PhotoRepository

class InsertPhotoSaveInfoUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photoSaveInfo: PhotoSaveInfo) {
        return repository.insertPhotoSaveInfo(photoSaveInfo)
    }
}