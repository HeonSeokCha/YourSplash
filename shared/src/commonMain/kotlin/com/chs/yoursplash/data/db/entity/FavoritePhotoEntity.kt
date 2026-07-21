package com.chs.yoursplash.data.db.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "favorite_photo")
data class FavoritePhotoEntity(
    @PrimaryKey
    val photoId: String,
    val photoUrl: String,
    val createTime: Long
)
