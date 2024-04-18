package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.data.db.entity.PhotoSaveEntity
import com.chs.yoursplash.domain.repository.PhotoRepository
import javax.inject.Inject

class InsertPhotoSaveInfoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photoSaveEntity: PhotoSaveEntity) {
        return repository.insertPhotoSaveInfo()
    }
}