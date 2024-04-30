package com.chs.yoursplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toUserDetail
import com.chs.yoursplash.data.model.ResponseUserDetail
import com.chs.yoursplash.data.paging.UserCollectionsDataSource
import com.chs.yoursplash.data.paging.UserLikesDataSource
import com.chs.yoursplash.data.paging.UserPhotosDataSource
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.domain.repository.UserRepository
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val client: UnSplashService
) : UserRepository {

    override suspend fun getUserDetail(userName: String): UserDetail {
        return client.requestUnsplash<ResponseUserDetail>(
            Constants.GET_USER_DETAILED(userName)
        ).toUserDetail()
    }

    override fun getUserDetailPhotos(userName: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            UserPhotosDataSource(client, userName)
        }.flow
    }

    override fun getUserDetailLikePhotos(userName: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            UserLikesDataSource(client, userName)
        }.flow
    }

    override fun getUserDetailCollections(userName: String): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            UserCollectionsDataSource(client, userName)
        }.flow
    }
}