package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserLikesUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userName: String): Flow<PagingData<Photo>> {
        return repository.getUserDetailLikePhotos(userName)
    }
}