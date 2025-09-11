package com.chs.yoursplash.domain.repository

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.util.NetworkResult
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUserDetail(userName: String): Flow<NetworkResult<UserDetail>>

    fun getUserDetailPhotos(userName: String, loadQuality: LoadQuality): Flow<PagingData<Photo>>

    fun getUserDetailLikePhotos(userName: String, loadQuality: LoadQuality): Flow<PagingData<Photo>>

    fun getUserDetailCollections(userName: String, loadQuality: LoadQuality): Flow<PagingData<UnSplashCollection>>
}