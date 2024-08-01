package com.chs.yoursplash.domain.usecase

import domain.model.PhotoSaveInfo
import domain.repository.PhotoRepository
import javax.inject.Inject

class DeletePhotoSaveInfoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photoSaveInfo: PhotoSaveInfo) {
        return repository.deletePhotoSaveInfo(photoSaveInfo)
    }
}