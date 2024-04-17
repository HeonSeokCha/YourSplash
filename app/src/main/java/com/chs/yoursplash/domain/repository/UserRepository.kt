package com.chs.yoursplash.domain.repository

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUserDetail(userName: String)

    fun getUserDetailPhotos(userName: String): Flow<PagingData<Photo>>

    fun getUserDetailLikePhotos(userName: String): Flow<PagingData<Photo>>

    fun getUserDetailCollections(userName: String): Flow<PagingData<UnSplashCollection>>
}