package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: SplashRepository
) {
    suspend operator fun invoke(userName: String): Flow<Resource<UserDetail>> {
        return repository.getUserDetail(userName)
    }
}