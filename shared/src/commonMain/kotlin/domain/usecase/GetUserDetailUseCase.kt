package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.domain.repository.UserRepository
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userName: String): Flow<NetworkResult<UserDetail>> {
        return repository.getUserDetail(userName)
    }
}