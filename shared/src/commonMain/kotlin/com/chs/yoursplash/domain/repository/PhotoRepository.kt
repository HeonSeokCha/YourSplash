package com.chs.yoursplash.domain.repository

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.util.NetworkResult
import com.chs.yoursplash.domain.model.UnSplashCollection
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun getPagingPhoto(loadQuality: LoadQuality): Flow<PagingData<Photo>>

    fun getPagingCollection(loadQuality: LoadQuality): Flow<PagingData<UnSplashCollection>>

    fun getPhotoDetailInfo(id: String): Flow<NetworkResult<PhotoDetail>>

    fun getRelatedPhotoList(id: String): Flow<NetworkResult<List<Photo>>>

    fun getCollectionDetailInfo(id: String): Flow<NetworkResult<UnSplashCollection>>

    fun getPagingCollectionPhotos(
        id: String,
        loadQuality: LoadQuality
    ): Flow<PagingData<Photo>>

    fun getRelatedCollectionList(id: String): Flow<NetworkResult<List<UnSplashCollection>>>

    fun requestFileDownload(
        fileName: String,
        url: String
    ): Flow<NetworkResult<Boolean>>

    suspend fun getFileIsExist(
        fileName: String
    ): Boolean
}