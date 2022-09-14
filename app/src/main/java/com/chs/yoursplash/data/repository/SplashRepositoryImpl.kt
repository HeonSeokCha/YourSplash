package com.chs.yoursplash.data.repository

import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.domain.model.UnSplashImageDetail
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow

class SplashRepositoryImpl : SplashRepository {
    override suspend fun getSplashImages(): Flow<Resource<List<UnSplashImage>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSplashImageDetail(id: String): Flow<Resource<List<UnSplashImageDetail>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchResultImages(
        query: String,
        page: Int,
        orderBy: String,
        color: String?,
        orientation: String?
    ): Flow<Resource<List<UnSplashImage>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchResultCollection(query: String, page: Int) {
        TODO("Not yet implemented")
    }
}