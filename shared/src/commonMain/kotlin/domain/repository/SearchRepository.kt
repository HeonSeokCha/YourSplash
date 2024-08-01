package com.chs.yoursplash.domain.repository

import androidx.paging.PagingData
import domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearchResultPhoto(
        query: String,
        orderBy: String,
        color: String? = null,
        orientation: String? = null
    ): Flow<PagingData<Photo>>

    fun getSearchResultCollection(query: String): Flow<PagingData<UnSplashCollection>>

    fun getSearchResultUser(query: String): Flow<PagingData<User>>

    suspend fun insertSearchHistory(query: String)

    suspend fun deleteSearchHistory(query: String)

    fun getRecentSearchHistory(): Flow<List<String>>
}