package com.chs.yoursplash.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chs.yoursplash.data.db.entity.PhotoSaveEntity

@Dao
abstract class PhotoSaveInfoDao : BaseDao<PhotoSaveEntity> {

    @Query("SELECT * FROM PhotoSaveInfo WHERE file_name = :fileName")
    abstract suspend fun checkSavePhoto(fileName: String): PhotoSaveEntity?

    @Query("DELETE FROM PhotoSaveInfo WHERE file_name = :fileName")
    abstract suspend fun deleteSavePhoto(fileName: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPhotoSaveInfo(photoSaveEntity: PhotoSaveEntity): Long
}