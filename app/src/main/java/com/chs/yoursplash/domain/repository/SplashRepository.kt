package com.chs.yoursplash.domain.repository

import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.domain.model.UnSplashImageDetail
import com.chs.yoursplash.domain.model.UnSplashRelated
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow

interface SplashRepository {

    suspend fun getSplashImages(): Flow<Resource<List<UnSplashImage>>>

    suspend fun getSplashCollection()

    suspend fun getSplashImageDetail(id: String): Flow<Resource<UnSplashImageDetail>>

    suspend fun getSplashImageRelated(id: String): Flow<Resource<List<UnSplashImage>>>

    suspend fun getSearchResultImages(
        query: String,
        page: Int,
        orderBy: String = "relevant",
        color: String? = null,
        orientation: String? = null
    ): Flow<Resource<List<UnSplashImage>>>

    suspend fun getSearchResultCollection(query: String, page: Int)

}