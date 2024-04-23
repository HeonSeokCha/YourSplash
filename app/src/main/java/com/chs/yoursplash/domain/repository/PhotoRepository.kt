package com.chs.yoursplash.domain.repository

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.model.PhotoSaveInfo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun getPagingPhoto(): Flow<PagingData<Photo>>

    fun getPagingCollection(): Flow<PagingData<UnSplashCollection>>

    suspend fun getPhotoDetailInfo(id: String): PhotoDetail

    suspend fun getRelatedPhotoList(id: String): List<Photo>

    suspend fun getCollectionDetailInfo(id: String): UnSplashCollection

    fun getPagingCollectionPhotos(id: String): Flow<PagingData<Photo>>

    suspend fun getRelatedCollectionList(id: String): List<UnSplashCollection>

    suspend fun getPhotoSaveInfo(): List<PhotoSaveInfo>

    suspend fun insertPhotoSaveInfo(photoSaveInfo: PhotoSaveInfo)

    suspend fun deletePhotoSaveInfo(photoSaveInfo: PhotoSaveInfo)
}