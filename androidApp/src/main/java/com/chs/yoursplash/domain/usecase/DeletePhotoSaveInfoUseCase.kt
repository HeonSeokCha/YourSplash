package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.PhotoSaveInfo
import com.chs.yoursplash.domain.repository.PhotoRepository
import javax.inject.Inject

class DeletePhotoSaveInfoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photoSaveInfo: PhotoSaveInfo) {
        return repository.deletePhotoSaveInfo(photoSaveInfo)
    }
}