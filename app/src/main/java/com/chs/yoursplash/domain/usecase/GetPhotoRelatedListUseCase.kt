package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoRelatedListUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(id: String): List<Photo> {
        return repository.getRelatedPhotoList(id)
    }
}