package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.PhotoSaveInfo
import domain.repository.PhotoRepository
import javax.inject.Inject

class GetPhotoSaveInfoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(): List<PhotoSaveInfo> {
        return repository.getPhotoSaveInfo()
    }
}