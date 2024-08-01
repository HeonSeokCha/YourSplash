package com.chs.yoursplash.domain.usecase

import domain.model.Photo
import domain.repository.PhotoRepository
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoRelatedListUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(id: String): Flow<NetworkResult<List<Photo>>> {
        return repository.getRelatedPhotoList(id)
    }
}