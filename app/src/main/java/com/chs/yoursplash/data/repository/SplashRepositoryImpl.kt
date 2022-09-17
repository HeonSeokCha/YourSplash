package com.chs.yoursplash.data.repository

import coil.network.HttpException
import com.chs.yoursplash.data.mapper.toUnSplashImage
import com.chs.yoursplash.data.source.UnSplashService
import com.chs.yoursplash.domain.model.UnSplashImage
import com.chs.yoursplash.domain.model.UnSplashImageDetail
import com.chs.yoursplash.domain.repository.SplashRepository
import com.chs.yoursplash.util.Resource
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val client: UnSplashService
) : SplashRepository {
    override suspend fun getSplashImages(): Flow<Resource<List<UnSplashImage>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                emit(Resource.Success(
                    client.getSplashImage().map {
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