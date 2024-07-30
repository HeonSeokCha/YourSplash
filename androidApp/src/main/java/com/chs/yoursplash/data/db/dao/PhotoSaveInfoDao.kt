package com.chs.yoursplash.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chs.yoursplash.data.db.entity.PhotoSaveEntity

@Dao
abstract class PhotoSaveInfoDao : BaseDao<PhotoSaveEntity> {

    @Query("SELECT * FROM photosaveinfo ORDER BY createTime DESC")
    abstract suspend fun getSavePhotoList(): List<PhotoSaveEntity>

}