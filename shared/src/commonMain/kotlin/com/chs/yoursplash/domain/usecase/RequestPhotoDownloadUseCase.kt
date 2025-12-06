package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.flow.Flow

class RequestPhotoDownloadUseCase(
    private val repository: PhotoRepository,
) {
    operator fun invoke(
        fileName: String,
        url: String
    ): Flow<NetworkResult<Boolean>> {
        return repository.requestFileDownload(fileName = fileName, url = url)
    }
}