package com.chs.yoursplash.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Upsert

interface BaseDao<T> {
    @Upsert
    suspend fun insertEntity(entity: T)

    @Delete
    suspend fun deleteEntity(entity: T)
}