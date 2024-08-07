package data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import data.db.dao.SearchHistoryDao
import data.db.entity.SearchHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class YourSplashDatabase : RoomDatabase(), DB {
    abstract val searchHistoryDao: SearchHistoryDao
    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}