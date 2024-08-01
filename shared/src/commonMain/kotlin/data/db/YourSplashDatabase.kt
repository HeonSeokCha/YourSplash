package data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import data.db.dao.SearchHistoryDao
import com.chs.yoursplash.data.db.entity.SearchHistoryEntity

@Database(
    entities = [
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class YourSplashDatabase : RoomDatabase() {
    abstract val searchHistoryDao: SearchHistoryDao
}