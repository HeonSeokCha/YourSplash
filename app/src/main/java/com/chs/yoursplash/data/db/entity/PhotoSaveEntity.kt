package com.chs.yoursplash.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhotoSaveInfo")
data class PhotoSaveEntity(
    @PrimaryKey
    val photoId: String,
    val blurHash: String,
    val photoUrl: String,
    val createTime: Long = System.currentTimeMillis()
)
