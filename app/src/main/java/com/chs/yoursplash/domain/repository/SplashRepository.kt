package com.chs.yoursplash.domain.repository

import com.chs.yoursplash.domain.model.*
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    suspend fun getSplashPhoto(): Flow<Resource<List<Photo>>>

    suspend fun getSplashCollection(): Flow<Resource<List<UnSplashCollection>>>

    suspend fun getSplashPhotoDetail(id: String): Flow<Resource<PhotoDetail>>

    suspend fun getSplashPhotoRelated(id: String): Flow<Resource<List<Photo>>>

    suspend fun getSplashCollectionDetail(id: String): Flow<Resource<UnSplashCollection>>

    suspend fun getUserDetail(id: String): Flow<Resource<User>>

    suspend fun getUserDetailPhotos(id: String): Flow<Resource<List<Photo>>>

    suspend fun getUserDetailLikePhotos(id: String): Flow<Resource<List<Photo>>>

    suspend fun getUserDetailCollections(id: String): Flow<Resource<List<UnSplashCollection>>>



    suspend fun getSearchResultSplashPhoto(
        query: String,
        page: Int,
        orderBy: String = "relevant",
        color: String? = null,
        orientation: String? = null
    ): Flow<Resource<List<Photo>>>

    suspend fun getSearchResultPhotoCollection(query: String, page: Int)

}