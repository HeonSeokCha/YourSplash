package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoDetailUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photoId: String): Flow<Resource<PhotoDetail>> {
        return repository.getSplashPhotoDetail(photoId)
    }
}