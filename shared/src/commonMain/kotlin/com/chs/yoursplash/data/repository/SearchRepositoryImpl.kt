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
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.SearchFilter
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SearchRepositoryImpl(
    private val client: UnSplashService,
    private val database: YourSplashDatabase,
    private val dataStore: DataStorePrefManager
) : SearchRepository {

    override suspend fun getSearchResultPhoto(
        query: String,
        searchFilter: SearchFilter
    ): Flow<PagingData<Photo>> {
        val loadQuality = getLoadQuality()
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            SearchPhotoPaging(
                api = client,
                query = query,
                searchFilter = searchFilter,
                loadQuality = loadQuality
            )
        }.flow
    }

    override suspend fun getSearchResultCollection(query: String): Flow<PagingData<UnSplashCollection>> {
        val loadQuality = getLoadQuality()
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            SearchCollectionPaging(
                api = client,
                query = query,
                loadQuality = loadQuality
            )
        }.flow
    }

    override suspend fun getSearchResultUser(query: String): Flow<PagingData<User>> {
        val loadQuality = getLoadQuality()
        return Pager(
            PagingConfig(pageSize = Constants.PAGING_SIZE)
        ) {
            SearchUserPaging(
                api = client,
                query = query,
                loadQuality = loadQuality
            )
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

    private suspend fun getLoadQuality(): LoadQuality {
        return dataStore.getData(
            keyName = Constants.PREFERENCE_KEY_LOAD_QUALITY,
            defaultValue = LoadQuality.Regular.name
        )
            .first()
            .run {
                LoadQuality.valueOf(this)
            }
    }
}