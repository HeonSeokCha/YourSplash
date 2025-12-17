package com.chs.yoursplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toUserDetail
import com.chs.yoursplash.data.model.ResponseUserDetail
import com.chs.yoursplash.data.paging.UserCollectionsPaging
import com.chs.yoursplash.data.paging.UserLikesPhotoPaging
import com.chs.yoursplash.data.paging.UserPhotosPaging
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.domain.repository.UserRepository
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val client: UnSplashService,
    private val dataStore: DataStorePrefManager
) : UserRepository {

    override suspend fun getUserDetail(userName: String): Flow<NetworkResult<UserDetail>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<ResponseUserDetail>(
                            url = Constants.GET_USER_DETAILED(
                                userName
                            )
                        ).toUserDetail(getLoadQuality())
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }
        }
    }

    override fun getUserDetailPhotos(
        userName: String,
        loadQuality: LoadQuality
    ): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            UserPhotosPaging(
                api = client,
                userName = userName,
                loadQuality = loadQuality
            )
        }.flow
    }

    override fun getUserDetailLikePhotos(
        userName: String,
        loadQuality: LoadQuality
    ): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            UserLikesPhotoPaging(
                api = client,
                userName = userName,
                loadQuality = loadQuality
            )
        }.flow
    }

    override fun getUserDetailCollections(
        userName: String,
        loadQuality: LoadQuality
    ): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            UserCollectionsPaging(
                api = client,
                userName = userName,
                loadQuality = loadQuality
            )
        }.flow
    }

    private suspend fun getLoadQuality(): LoadQuality {
        return dataStore.getData(
            keyName = Constants.PREFERENCE_KEY_LOAD_QUALITY,
            defaultValue = LoadQuality.Regular.name
        )
            .first()
            .run {
                LoadQuality.valueOf(this)
            }
    }
}