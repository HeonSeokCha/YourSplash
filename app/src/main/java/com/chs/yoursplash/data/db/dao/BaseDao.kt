package com.chs.yoursplash.data.db.dao

import androidx.room.Delete
import androidx.room.Insert

interface BaseDao<T> {
    @Insert
    suspend fun insertEntity(entity: T)

    @Delete
    suspend fun deleteEntity(entity: T)
}