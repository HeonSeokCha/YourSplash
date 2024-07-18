package com.chs.yoursplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.db.dao.PhotoSaveInfoDao
import com.chs.yoursplash.data.mapper.toPhotoCollection
import com.chs.yoursplash.data.mapper.toPhotoSaveEntity
import com.chs.yoursplash.data.mapper.toPhotoSaveInfo
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.mapper.toUnSplashImageDetail
import com.chs.yoursplash.data.model.ResponseCollection
import com.chs.yoursplash.data.model.ResponsePhotoDetail
import com.chs.yoursplash.data.model.ResponseRelatedPhoto
import com.chs.yoursplash.data.paging.CollectionPhotoPaging
import com.chs.yoursplash.data.paging.HomeCollectionPaging
import com.chs.yoursplash.data.paging.HomePhotosPaging
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.model.PhotoSaveInfo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val client: UnSplashService,
    private val photoSaveInfoDao: PhotoSaveInfoDao,
) : PhotoRepository {
    override fun getPagingPhoto(): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            HomePhotosPaging(client)
        }.flow
    }

    override fun getPagingCollection(): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            HomeCollectionPaging(client)
        }.flow
    }

    override suspend fun getPhotoDetailInfo(id: String): Flow<NetworkResult<PhotoDetail>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<ResponsePhotoDetail>(
                            Constants.GET_PHOTO_DETAIL(id)
                        ).toUnSplashImageDetail()
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }
        }
    }

    override suspend fun getRelatedPhotoList(id: String): Flow<NetworkResult<List<Photo>>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<ResponseRelatedPhoto>(
                            Constants.GET_PHOTO_RELATED(id)
                        ).results.map { it.toUnSplashImage() }
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }

        }
    }

    override suspend fun getCollectionDetailInfo(id: String): Flow<NetworkResult<UnSplashCollection>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<ResponseCollection>(
                            Constants.GET_COLLECTION_DETAILED(id)
                        ).toPhotoCollection()
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }
        }
    }

    override fun getPagingCollectionPhotos(id: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            CollectionPhotoPaging(client, id)
        }.flow
    }

    override suspend fun getRelatedCollectionList(id: String): Flow<NetworkResult<List<UnSplashCollection>>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<List<ResponseCollection>>(
                            Constants.GET_COLLECTION_RELATED(id)
                        ).map {
                            it.toPhotoCollection()
                        }
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }
        }
    }

    override suspend fun getPhotoSaveInfo(): List<PhotoSaveInfo> {
        return photoSaveInfoDao.getSavePhotoList().map {
            it.toPhotoSaveInfo()
        }
    }

    override suspend fun insertPhotoSaveInfo(photoSaveInfo: PhotoSaveInfo) {
        photoSaveInfoDao.insertEntity(
            photoSaveInfo.toPhotoSaveEntity()
        )
    }

    override suspend fun deletePhotoSaveInfo(photoSaveInfo: PhotoSaveInfo) {
        photoSaveInfoDao.deleteEntity(
            photoSaveInfo.toPhotoSaveEntity()
        )
    }
}