package com.chs.yoursplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.db.dao.PhotoSaveInfoDao
import com.chs.yoursplash.data.mapper.toPhotoCollection
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.mapper.toUnSplashImageDetail
import com.chs.yoursplash.data.model.ResponseCollection
import com.chs.yoursplash.data.model.ResponsePhotoDetail
import com.chs.yoursplash.data.model.ResponseRelatedPhoto
import com.chs.yoursplash.data.paging.CollectionPhotoDataSource
import com.chs.yoursplash.data.paging.HomePhotosDataSource
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val client: UnSplashService,
    private val photoSaveInfoDao: PhotoSaveInfoDao,
): PhotoRepository {
    override fun getPagingPhoto(): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            HomePhotosDataSource(client)
        }.flow
    }

    override suspend fun getPhotoDetailInfo(id: String) {
        client.requestUnsplash<ResponsePhotoDetail>(
            Constants.GET_PHOTO_DETAIL(id)
        ).toUnSplashImageDetail()
    }

    override suspend fun getRelatedPhotoList(id: String) {
        client.requestUnsplash<ResponseRelatedPhoto>(
            Constants.GET_PHOTO_RELATED(id)
        ).results.map { it.toUnSplashImage() }
    }

    override suspend fun getCollectionDetailInfo(id: String) {
        client.requestUnsplash<ResponseCollection>(
            Constants.GET_COLLECTION_DETAILED(id)
        ).toPhotoCollection()
    }

    override fun getPagingCollectionPhotos(id: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = 10)
        ) {
            CollectionPhotoDataSource(client, id)
        }.flow
    }

    override suspend fun getRelatedCollectionList(id: String) {
        client.requestUnsplash<List<ResponseCollection>>(
            Constants.GET_COLLECTION_RELATED(id)
        ).map {
            it.toPhotoCollection()
        }
    }

    override suspend fun getPhotoSaveInfo() {
    }

    override suspend fun insertPhotoSaveInfo() {
    }

    override suspend fun deletePhotoSaveInfo() {
    }
}