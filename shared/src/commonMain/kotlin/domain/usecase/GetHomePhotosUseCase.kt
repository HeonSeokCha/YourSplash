package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import domain.model.Photo
import domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomePhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    operator fun invoke(): Flow<PagingData<Photo>> {
        return repository.getPagingPhoto()
    }
}