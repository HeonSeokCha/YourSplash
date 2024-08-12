package data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import data.db.dao.SearchHistoryDao
import data.db.entity.SearchHistoryEntity

@Database(entities = [SearchHistoryEntity::class], version = 1)
abstract class YourSplashDatabase : RoomDatabase(), DB {
    abstract val searchHistoryDao: SearchHistoryDao
    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}