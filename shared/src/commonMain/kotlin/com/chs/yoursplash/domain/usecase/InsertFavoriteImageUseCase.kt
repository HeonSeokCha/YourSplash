package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.PhotoRepository
import org.koin.core.annotation.Single

@Single
class InsertFavoriteImageUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(id: String, url: String) =
        repository.insertFavoritePhoto(id, url)
}
