package com.chs.yoursplash.data.db.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.chs.yoursplash.data.db.entity.FavoriteImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FavoriteImageDao : BaseDao<FavoriteImageEntity> {

    @Query("SELECT * FROM favorite_image ORDER BY createTime DESC")
    abstract suspend fun getImageList(): List<FavoriteImageEntity>

    @Query("SELECT * FROM favorite_image WHERE imageId = :imageId")
    abstract fun getFavoriteState(imageId: String): Flow<FavoriteImageEntity?>

    @Query("DELETE FROM favorite_image where imageId = :imageId")
    abstract suspend fun deleteFromId(imageId: String)
}