package com.chs.yoursplash.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhotoSaveInfo")
data class PhotoSaveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "create_date")
    val createDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "file_name")
    val fileName: String
)
