package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionPhotoUserCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(id: String): Flow<PagingData<Photo>> {
        return repository.getSplashCollectionPhotos(id)
    }
}