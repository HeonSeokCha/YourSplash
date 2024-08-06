package data.db.dao

import androidx.room.Dao
import androidx.room.Query
import data.db.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SearchHistoryDao : BaseDao<SearchHistoryEntity> {

    @Query(
        "SELECT searchQuery " +
          "FROM search_history " +
         "ORDER BY lastSearchTime DESC LIMIT 10"
    )
    abstract fun getRecentList(): Flow<List<String>>
}