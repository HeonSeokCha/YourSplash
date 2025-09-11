package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetUserPhotoUseCase(
    private val repository: UserRepository,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) {
    operator fun invoke(userName: String): Flow<PagingData<Photo>> = flow {
        emit(getLoadQualityUseCase().first())
    }.flatMapLatest {
        repository.getUserDetailPhotos(userName = userName, loadQuality = it)
    }
}