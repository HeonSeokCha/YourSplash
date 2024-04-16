package com.chs.yoursplash.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chs.yoursplash.data.db.dao.PhotoSaveInfoDao
import com.chs.yoursplash.data.db.dao.SearchHistoryDao
import com.chs.yoursplash.data.db.entity.PhotoSaveEntity
import com.chs.yoursplash.data.db.entity.SearchHistoryEntity

@Database(
    entities = [
        PhotoSaveEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class YourSplashDatabase : RoomDatabase() {
    abstract val photoSaveInfoDao: PhotoSaveInfoDao
    abstract val searchHistoryDao: SearchHistoryDao
}