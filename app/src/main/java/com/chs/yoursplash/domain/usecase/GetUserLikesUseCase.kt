package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLikesUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    operator fun invoke(userName: String): Flow<PagingData<Photo>> {
        return repository.getUserDetailLikePhotos(userName)
    }
}