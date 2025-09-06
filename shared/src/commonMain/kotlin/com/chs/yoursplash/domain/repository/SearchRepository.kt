package com.chs.yoursplash.domain.repository

import androidx.paging.PagingData
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearchResultPhoto(
        query: String,
        searchFilter: SearchFilter,
        loadQuality: LoadQuality
    ): Flow<PagingData<Photo>>

    fun getSearchResultCollection(
        query: String,
        loadQuality: LoadQuality
    ): Flow<PagingData<UnSplashCollection>>

    fun getSearchResultUser(
        query: String,
        loadQuality: LoadQuality
    ): Flow<PagingData<User>>

    suspend fun insertSearchHistory(query: String)

    suspend fun deleteSearchHistory(query: String)

    fun getRecentSearchHistory(): Flow<List<String>>
}