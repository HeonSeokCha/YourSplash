package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.repository.PhotoRepository
import org.koin.core.annotation.Single

@Single
class InsertFavoriteImageUseCase(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photoDetail: PhotoDetail) = repository.insertFavoritePhoto(photoDetail)
}
