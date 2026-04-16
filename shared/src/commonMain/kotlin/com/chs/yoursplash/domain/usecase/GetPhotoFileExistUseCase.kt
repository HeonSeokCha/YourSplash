package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.PhotoRepository
import org.koin.core.annotation.Single

@Single
class GetPhotoFileExistUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(fileName: String): Boolean {
        return repository.getFileIsExist(fileName)
    }
}