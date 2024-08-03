package domain.usecase

import domain.model.PhotoSaveInfo
import domain.repository.PhotoRepository

class GetPhotoSaveInfoUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(): List<PhotoSaveInfo> {
        return repository.getPhotoSaveInfo()
    }
}