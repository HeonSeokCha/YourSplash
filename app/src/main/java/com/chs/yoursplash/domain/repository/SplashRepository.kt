package com.chs.yoursplash.domain.repository

import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.PhotoDetail
import com.chs.yoursplash.domain.model.RelatedPhotoCollection
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    suspend fun getSplashPhoto(): Flow<Resource<List<Photo>>>

    suspend fun getSplashCollection()

    suspend fun getSplashPhotoDetail(id: String): Flow<Resource<PhotoDetail>>

    suspend fun getSplashPhotoRelated(id: String): Flow<Resource<List<Photo>>>

    suspend fun getSearchResultSplashPhoto(
        query: String,
        page: Int,
        orderBy: String = "relevant",
        color: String? = null,
        orientation: String? = null
    ): Flow<Resource<List<Photo>>>

    suspend fun getSearchResultPhotoCollection(query: String, page: Int)

}