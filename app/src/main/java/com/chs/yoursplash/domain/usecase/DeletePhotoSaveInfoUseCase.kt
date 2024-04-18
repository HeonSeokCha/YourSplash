package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.PhotoRepository
import javax.inject.Inject

class DeletePhotoSaveInfoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(fileName: String) {
        return repository.deletePhotoSaveInfo()
    }
}