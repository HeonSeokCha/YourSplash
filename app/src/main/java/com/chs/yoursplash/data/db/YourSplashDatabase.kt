package com.chs.yoursplash.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PhotoSaveInfo::class],
    version = 1,
    exportSchema = false
)
abstract class YourSplashDatabase : RoomDatabase() {
    abstract val photoSaveInfoDao: PhotoSaveInfoDao
}