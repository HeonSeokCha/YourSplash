package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.domain.repository.UserRepository
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetUserDetailUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userName: String): Flow<NetworkResult<UserDetail>> {
        return repository.getUserDetail(userName)
    }
}