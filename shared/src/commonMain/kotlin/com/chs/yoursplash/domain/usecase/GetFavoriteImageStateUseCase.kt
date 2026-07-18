package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetFavoriteImageStateUseCase(
    private val repository: PhotoRepository
) {
    operator fun invoke(photoId: String): Flow<Boolean> {
        return repository.getFavoriteState(photoId)
    }
}