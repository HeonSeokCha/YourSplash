package com.chs.yoursplash.data.db.dao

import androidx.room3.Delete
import androidx.room3.Upsert

interface BaseDao<T> {
    @Upsert
    suspend fun insertEntity(entity: T)

    @Delete
    suspend fun deleteEntity(entity: T)
}