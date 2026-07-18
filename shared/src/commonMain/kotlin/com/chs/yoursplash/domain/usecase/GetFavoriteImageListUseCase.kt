package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.FavoritePhoto
import com.chs.yoursplash.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetFavoriteImageListUseCase(
    private val repository: PhotoRepository
) {
    operator fun invoke(): Flow<List<FavoritePhoto>> {
        return repository.getFavoritePhotoList()
    }
}