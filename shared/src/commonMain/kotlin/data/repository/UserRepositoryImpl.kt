package data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import data.api.UnSplashService
import data.mapper.toUserDetail
import com.chs.yoursplash.data.model.ResponseUserDetail
import data.paging.UserCollectionsPaging
import data.paging.UserLikesPhotoPaging
import data.paging.UserPhotosPaging
import domain.model.Photo
import domain.repository.UserRepository
import com.chs.yoursplash.util.NetworkResult
import domain.model.UnSplashCollection
import domain.model.UserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl (
    private val client: UnSplashService
) : UserRepository {

    override suspend fun getUserDetail(userName: String): Flow<NetworkResult<UserDetail>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<ResponseUserDetail>(
                            Constants.GET_USER_DETAILED(userName)
                        ).toUserDetail()
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }
        }
    }

    override fun getUserDetailPhotos(userName: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            UserPhotosPaging(client, userName)
        }.flow
    }

    override fun getUserDetailLikePhotos(userName: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            UserLikesPhotoPaging(client, userName)
        }.flow
    }

    override fun getUserDetailCollections(userName: String): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            UserCollectionsPaging(client, userName)
        }.flow
    }
}