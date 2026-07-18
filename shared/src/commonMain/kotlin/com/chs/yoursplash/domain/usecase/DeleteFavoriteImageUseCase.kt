package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.PhotoRepository
import org.koin.core.annotation.Single

@Single
class DeleteFavoriteImageUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(imageId: String) = repository.deleteFavoritePhoto(imageId)
}