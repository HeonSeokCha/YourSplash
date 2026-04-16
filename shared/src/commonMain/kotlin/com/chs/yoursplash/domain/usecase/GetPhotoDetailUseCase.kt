package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetPhotoDetailUseCase(
    private val repository: PhotoRepository
) {
    operator fun invoke(photoId: String): Flow<NetworkResult<PhotoDetail>> {
        return repository.getPhotoDetailInfo(photoId)
    }
}