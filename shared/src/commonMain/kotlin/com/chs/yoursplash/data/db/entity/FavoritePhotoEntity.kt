package com.chs.yoursplash.data.db.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "favorite_photo")
data class FavoritePhotoEntity(
    @PrimaryKey
    val imageId: String,
    val imageUrl: String,
    val createTime: Long
)
