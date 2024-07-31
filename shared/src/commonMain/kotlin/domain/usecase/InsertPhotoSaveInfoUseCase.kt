package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.PhotoSaveInfo
import domain.repository.PhotoRepository
import javax.inject.Inject

class InsertPhotoSaveInfoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photoSaveInfo: PhotoSaveInfo) {
        return repository.insertPhotoSaveInfo(photoSaveInfo)
    }
}