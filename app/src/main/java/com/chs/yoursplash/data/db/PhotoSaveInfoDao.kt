package com.chs.yoursplash.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoSaveInfoDao {

    @Query("SELECT * FROM PhotoSaveInfo WHERE file_name = :fileName")
    suspend fun checkSavePhoto(fileName: String): PhotoSaveInfo?

    @Query("DELETE FROM PhotoSaveInfo WHERE file_name = :fileName")
    suspend fun deleteSavePhoto(fileName: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotoSaveInfo(photoSaveInfo: PhotoSaveInfo): Long
}