package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetHomePhotosUseCase(
    private val repository: PhotoRepository,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) {
    operator fun invoke(): Flow<PagingData<Photo>> = flow {
        emit(getLoadQualityUseCase().first())
    }.flatMapLatest {
        repository.getPagingPhoto(it)
    }
}