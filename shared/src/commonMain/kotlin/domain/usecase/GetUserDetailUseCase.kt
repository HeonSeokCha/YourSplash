package domain.usecase

import com.chs.yoursplash.domain.repository.UserRepository
import com.chs.yoursplash.util.NetworkResult
import domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

class GetUserDetailUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userName: String): Flow<NetworkResult<UserDetail>> {
        return repository.getUserDetail(userName)
    }
}