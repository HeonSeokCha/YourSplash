package com.chs.yoursplash.data.db.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.chs.yoursplash.data.db.entity.FavoritePhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FavoritePhotoDao : BaseDao<FavoritePhotoEntity> {

    @Query("SELECT * FROM favorite_photo ORDER BY createTime DESC")
    abstract fun getImageList(): Flow<List<FavoritePhotoEntity>>

    @Query("SELECT * FROM favorite_photo WHERE photoId = :photoId")
    abstract fun getFavoriteState(photoId: String): Flow<FavoritePhotoEntity?>

    @Query("DELETE FROM favorite_photo where photoId = :photoId")
    abstract suspend fun deleteFromId(photoId: String)
}