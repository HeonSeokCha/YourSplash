package data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import data.api.UnSplashService
import data.db.dao.SearchHistoryDao
import com.chs.yoursplash.data.db.entity.SearchHistoryEntity
import data.paging.SearchCollectionPaging
import data.paging.SearchPhotoPaging
import data.paging.SearchUserPaging
import domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.model.User
import com.chs.yoursplash.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val client: UnSplashService,
    private val searchHistoryDao: SearchHistoryDao
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
        searchHistoryDao.insertEntity(
            SearchHistoryEntity(searchQuery = query)
        )
    }

    override suspend fun deleteSearchHistory(query: String) {
        searchHistoryDao.deleteEntity(
            SearchHistoryEntity(searchQuery = query)
        )
    }

    override fun getRecentSearchHistory(): Flow<List<String>> {
        return searchHistoryDao.getRecentList()
    }
}