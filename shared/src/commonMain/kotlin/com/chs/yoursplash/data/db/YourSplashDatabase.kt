package com.chs.yoursplash.data.db

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.chs.yoursplash.data.db.dao.FavoriteImageDao
import com.chs.yoursplash.data.db.dao.SearchHistoryDao
import com.chs.yoursplash.data.db.entity.FavoriteImageEntity
import com.chs.yoursplash.data.db.entity.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class, FavoriteImageEntity::class],
    version = 1,
    exportSchema = false
)
@ConstructedBy(YourSplashDatabaseConstructor::class)
abstract class YourSplashDatabase : RoomDatabase() {
    abstract val searchHistoryDao: SearchHistoryDao
    abstract val favoriteImageDao: FavoriteImageDao
}