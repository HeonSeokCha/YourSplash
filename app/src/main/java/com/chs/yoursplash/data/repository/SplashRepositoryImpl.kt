package com.chs.yoursplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import coil.network.HttpException
import com.chs.yoursplash.data.mapper.*
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.db.PhotoSaveInfo
import com.chs.yoursplash.data.db.YourSplashDatabase
import com.chs.yoursplash.data.model.ResponseCollection
import com.chs.yoursplash.data.model.ResponsePhotoDetail
import com.chs.yoursplash.data.model.ResponseRelatedPhoto
import com.chs.yoursplash.data.model.ResponseUserDetail
import com.chs.yoursplash.data.paging.*
import com.chs.yoursplash.domain.model.*
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val client: UnSplashService, private val db: YourSplashDatabase
) : SplashRepository {
    override fun getSplashPhoto(): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            HomePhotosDataSource(client)
        }.flow
    }

    override fun getSplashCollection(): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            HomeCollectionDataSource(client)
        }.flow
    }

    override suspend fun getSplashPhotoDetail(id: String): Flow<Resource<PhotoDetail>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        (client.requestUnsplash(
                            Constants.GET_PHOTO_DETAIL(id)
                        ) as ResponsePhotoDetail).toUnSplashImageDetail()
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }

    override suspend fun getSplashPhotoRelated(id: String): Flow<Resource<List<Photo>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(Resource.Success((client.requestUnsplash(
                    Constants.GET_PHOTO_RELATED(id)
                ) as ResponseRelatedPhoto).results.map { it.toUnSplashImage() }))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }

    override suspend fun getSplashCollectionDetail(id: String): Flow<Resource<UnSplashCollection>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        (client.requestUnsplash(
                            Constants.GET_COLLECTION_DETAILED(id)
                        ) as ResponseCollection).toPhotoCollection()
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }

    override fun getSplashCollectionPhotos(id: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            CollectionPhotoDataSource(client, id)
        }.flow
    }

    override suspend fun getSplashCollectionRelated(id: String): Flow<Resource<List<UnSplashCollection>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(Resource.Success((client.requestUnsplash(
                    Constants.GET_COLLECTION_RELATED(id)
                ) as List<ResponseCollection>).map { it.toPhotoCollection() }))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }

    override suspend fun getUserDetail(userName: String): Flow<Resource<UserDetail>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        (client.requestUnsplash(
                            Constants.GET_USER_DETAILED(userName)
                        ) as ResponseUserDetail).toUserDetail()
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }

    override fun getUserDetailPhotos(userName: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            UserPhotosDataSource(client, userName)
        }.flow
    }

    override fun getUserDetailLikePhotos(userName: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            UserLikesDataSource(client, userName)
        }.flow
    }

    override fun getUserDetailCollections(userName: String): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            UserCollectionsDataSource(client, userName)
        }.flow
    }

    override fun getSearchResultPhoto(
        query: String, orderBy: String, color: String?, orientation: String?
    ): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchPhotoPaging(
                api = client,
                query = query,
                orderBy = orderBy,
                color = color,
                orientation = orientation
            )
        }.flow
    }

    override fun getSearchResultCollection(query: String): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchCollectionPaging(client, query)
        }.flow
    }

    override fun getSearchResultUser(query: String): Flow<PagingData<User>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            SearchUserPaging(client, query)
        }.flow
    }

    override suspend fun getSavePhoto(fileName: String): PhotoSaveInfo? {
        return db.photoSaveInfoDao.checkSavePhoto(fileName)
    }

    override suspend fun deleteSavePhotoInfo(fileName: String): Int {
        return db.photoSaveInfoDao.deleteSavePhoto(fileName)
    }

    override suspend fun insertSavePhotoInfo(photoSaveInfo: PhotoSaveInfo): Long {
        return db.photoSaveInfoDao.insertPhotoSaveInfo(photoSaveInfo)
    }
}