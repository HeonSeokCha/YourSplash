package com.chs.yoursplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.mapper.toPhotoCollection
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.mapper.toUnSplashImageDetail
import com.chs.yoursplash.data.model.ResponseCollection
import com.chs.yoursplash.data.model.ResponsePhotoDetail
import com.chs.yoursplash.data.model.ResponseRelatedPhoto
import com.chs.yoursplash.data.paging.CollectionPhotoPaging
import com.chs.yoursplash.data.paging.HomeCollectionPaging
import com.chs.yoursplash.data.paging.HomePhotosPaging
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PhotoRepositoryImpl(
    private val client: UnSplashService,
    private val dataStore: DataStorePrefManager,
    private val fileManager: FileManager
) : PhotoRepository {
    override fun getPagingPhoto(loadQuality: LoadQuality): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            HomePhotosPaging(
                api = client,
                loadQuality = loadQuality
            )
        }.flow
    }

    override fun getPagingCollection(loadQuality: LoadQuality): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            HomeCollectionPaging(
                api = client,
                loadQuality = loadQuality
            )
        }.flow
    }

    override fun getPhotoDetailInfo(id: String): Flow<NetworkResult<PhotoDetail>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<ResponsePhotoDetail>(
                            Constants.GET_PHOTO_DETAIL(id)
                        ).toUnSplashImageDetail(
                            loadQuality = getLoadQuality(Constants.PREFERENCE_KEY_LOAD_QUALITY),
                            downloadQuality = getLoadQuality(Constants.PREFERENCE_KEY_DOWNLOAD_QUALITY)
                        )
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }
        }
    }

    override fun getRelatedPhotoList(id: String): Flow<NetworkResult<List<Photo>>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<ResponseRelatedPhoto>(
                            Constants.GET_PHOTO_RELATED(id)
                        ).results.map {
                            it.toUnSplashImage(
                                quality = getLoadQuality(Constants.PREFERENCE_KEY_LOAD_QUALITY)
                            )
                        }
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }

        }
    }

    override fun getCollectionDetailInfo(id: String): Flow<NetworkResult<UnSplashCollection>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<ResponseCollection>(
                            Constants.GET_COLLECTION_DETAILED(id)
                        ).toPhotoCollection(getLoadQuality(Constants.PREFERENCE_KEY_LOAD_QUALITY))
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }
        }
    }

    override fun getPagingCollectionPhotos(
        id: String,
        loadQuality: LoadQuality
    ): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            CollectionPhotoPaging(
                api = client,
                collectionId = id,
                loadQuality = loadQuality
            )
        }.flow
    }

    override fun getRelatedCollectionList(id: String): Flow<NetworkResult<List<UnSplashCollection>>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                emit(
                    NetworkResult.Success(
                        client.requestUnsplash<List<ResponseCollection>>(
                            Constants.GET_COLLECTION_RELATED(id)
                        ).map {
                            it.toPhotoCollection(getLoadQuality(Constants.PREFERENCE_KEY_LOAD_QUALITY))
                        }
                    )
                )
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }
        }
    }

    private suspend fun getLoadQuality(keyName: String): LoadQuality {
        return dataStore.getData(
            keyName = keyName,
            defaultValue = LoadQuality.Regular.name
        )
            .first()
            .run {
                LoadQuality.valueOf(this)
            }
    }

    override fun requestFileDownload(
        fileName: String,
        url: String
    ): Flow<NetworkResult<Boolean>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                val responseByteArray: ByteArray = client.requestUnsplash(url = url)
                val saveResult = fileManager.saveFile(
                    fileName = fileName,
                    data = responseByteArray
                )

                emit(NetworkResult.Success(saveResult.isSuccess))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown Error..."))
            }
        }
    }

    override suspend fun getFileIsExist(fileName: String): Boolean {
        return try {
            fileManager.isFileExist(fileName).isSuccess
        } catch (e: Exception) {
            false
        }
    }
}