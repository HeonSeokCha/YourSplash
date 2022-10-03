package com.chs.yoursplash.data.repository

import coil.network.HttpException
import com.chs.yoursplash.data.mapper.toPhotoCollection
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.mapper.toUnSplashImageDetail
import com.chs.yoursplash.data.mapper.toUnSplashUser
import com.chs.yoursplash.data.source.UnSplashService
import com.chs.yoursplash.domain.model.*
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val client: UnSplashService
) : SplashRepository {
    override suspend fun getSplashPhoto(): Flow<Resource<List<Photo>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(Resource.Success(
                    client.getPhotos().map {
                        it.toUnSplashImage()
                    }
                ))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }

    override suspend fun getSplashCollection(): Flow<Resource<List<UnSplashCollection>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(Resource.Success(
                    client.getCollection().map {
                        it.toPhotoCollection()
                    }
                ))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load date"))
            }
        }
    }

    override suspend fun getSplashPhotoDetail(id: String): Flow<Resource<PhotoDetail>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(Resource.Success(
                    client.getPhotoDetail(id).toUnSplashImageDetail())
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
                emit(
                    Resource.Success(
                        client.getPhotoRelated(id).results.map {
                            it.toUnSplashImage()
                        }
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

    override suspend fun getSplashCollectionDetail(id: String): Flow<Resource<UnSplashCollection>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        client.getCollectionDetail(id).toPhotoCollection()
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

    override suspend fun getUserDetail(userName: String): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        client.getUserDetail(userName).toUnSplashUser()
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

    override suspend fun getUserDetailPhotos(userName: String): Flow<Resource<List<Photo>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        client.getUserPhotos(userName).map {
                            it.toUnSplashImage()
                        }
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

    override suspend fun getUserDetailLikePhotos(userName: String): Flow<Resource<List<Photo>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        client.getUserLikes(userName).map {
                            it.toUnSplashImage()
                        }
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

    override suspend fun getUserDetailCollections(userName: String): Flow<Resource<List<UnSplashCollection>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(
                    Resource.Success(
                        client.getUserCollections(userName).map {
                            it.toPhotoCollection()
                        }
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

    override suspend fun getSearchResultSplashPhoto(
        query: String,
        page: Int,
        orderBy: String,
        color: String?,
        orientation: String?
    ): Flow<Resource<List<Photo>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchResultPhotoCollection(query: String, page: Int) {
        TODO("Not yet implemented")
    }
}