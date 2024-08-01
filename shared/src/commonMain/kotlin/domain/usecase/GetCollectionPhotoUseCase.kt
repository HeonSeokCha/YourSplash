package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import domain.model.Photo
import domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(id: String): Flow<PagingData<Photo>> {
        return repository.getPagingCollectionPhotos(id)
    }
}