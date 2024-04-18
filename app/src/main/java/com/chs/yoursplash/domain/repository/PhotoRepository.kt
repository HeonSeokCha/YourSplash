package com.chs.yoursplash.domain.repository

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun getPagingPhoto(): Flow<PagingData<Photo>>

    suspend fun getPhotoDetailInfo(id: String)

    suspend fun getRelatedPhotoList(id: String)

    suspend fun getCollectionDetailInfo(id: String)

    fun getPagingCollectionPhotos(id: String): Flow<PagingData<Photo>>

    suspend fun getRelatedCollectionList(id: String)

    suspend fun getPhotoSaveInfo()


    suspend fun insertPhotoSaveInfo()

    suspend fun deletePhotoSaveInfo()
}