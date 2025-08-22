package com.chs.yoursplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.db.YourSplashDatabase
import com.chs.yoursplash.data.db.entity.SearchHistoryEntity
import com.chs.yoursplash.data.paging.SearchCollectionPaging
import com.chs.yoursplash.data.paging.SearchPhotoPaging
import com.chs.yoursplash.data.paging.SearchUserPaging
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val client: UnSplashService,
    private val database: YourSplashDatabase
) : SearchRepository {

    override fun getSearchResultPhoto(
        query: String,
        orderBy: String,
        color: String?,
        orientation: String?
    ): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            SearchPhotoPaging(
                api = client,
                query = query,
                orderBy = orderBy,
                color = color,
                orientation = orientation
            )
        }.flow
    }

    override fun getSearchResultCollection(query: String): Flow<PagingData<UnSplashCollection>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            SearchCollectionPaging(client, query)
        }.flow
    }

    override fun getSearchResultUser(query: String): Flow<PagingData<User>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            SearchUserPaging(client, query)
        }.flow
    }

    override suspend fun insertSearchHistory(query: String) {
        database.searchHistoryDao.insertEntity(
            SearchHistoryEntity(searchQuery = query)
        )
    }

    override suspend fun deleteSearchHistory(query: String) {
        database.searchHistoryDao.deleteEntity(
            SearchHistoryEntity(searchQuery = query)
        )
    }

    override fun getRecentSearchHistory(): Flow<List<String>> {
        return database.searchHistoryDao.getRecentList()
    }
}