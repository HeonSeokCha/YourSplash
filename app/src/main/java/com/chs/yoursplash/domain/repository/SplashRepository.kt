package com.chs.yoursplash.domain.repository

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.*
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    fun getSplashPhoto(): Flow<PagingData<Photo>>

    fun getSplashCollection(): Flow<PagingData<UnSplashCollection>>

    suspend fun getSplashPhotoDetail(id: String): Flow<Resource<PhotoDetail>>

    suspend fun getSplashPhotoRelated(id: String): Flow<Resource<List<Photo>>>

    suspend fun getSplashCollectionDetail(id: String): Flow<Resource<UnSplashCollection>>

    fun getSplashCollectionPhotos(id: String): Flow<PagingData<Photo>>

    suspend fun getSplashCollectionRelated(id: String): Flow<Resource<List<UnSplashCollection>>>

    suspend fun getUserDetail(userName: String): Flow<Resource<UserDetail>>

    fun getUserDetailPhotos(userName: String): Flow<PagingData<Photo>>

    fun getUserDetailLikePhotos(userName: String): Flow<PagingData<Photo>>

    fun getUserDetailCollections(userName: String): Flow<PagingData<UnSplashCollection>>



    fun getSearchResultPhoto(
        query: String,
        orderBy: String = "relevant",
        color: String? = null,
        orientation: String? = null
    ): Flow<PagingData<Photo>>

    fun getSearchResultCollection(query: String): Flow<PagingData<UnSplashCollection>>

    fun getSearchResultUser(query: String): Flow<PagingData<User>>

}