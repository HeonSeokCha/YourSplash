package com.chs.yoursplash.domain.repository

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getSearchResultPhoto(
        query: String,
        searchFilter: SearchFilter
): Flow<PagingData<Photo>>

    suspend fun getSearchResultCollection(query: String): Flow<PagingData<UnSplashCollection>>

    suspend fun getSearchResultUser(query: String): Flow<PagingData<User>>

    suspend fun insertSearchHistory(query: String)

    suspend fun deleteSearchHistory(query: String)

    fun getRecentSearchHistory(): Flow<List<String>>
}