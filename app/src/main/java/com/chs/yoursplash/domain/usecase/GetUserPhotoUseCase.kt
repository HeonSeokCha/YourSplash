package com.chs.yoursplash.domain.usecase

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPhotoUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userName: String): Flow<PagingData<Photo>> {
        return repository.getUserDetailPhotos(userName)
    }
}