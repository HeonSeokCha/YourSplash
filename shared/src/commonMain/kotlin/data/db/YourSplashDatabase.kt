package data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import data.db.dao.SearchHistoryDao
import data.db.entity.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
@ConstructedBy(YourSplashDatabaseConstructor::class)
abstract class YourSplashDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME: String = "animeList.db"
    }
    abstract val searchHistoryDao: SearchHistoryDao
}