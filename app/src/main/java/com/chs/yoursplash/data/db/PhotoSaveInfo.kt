package com.chs.yoursplash.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhotoSaveInfo")
data class PhotoSaveInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "create_date")
    val createDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "file_name")
    val fileName: String,
)
