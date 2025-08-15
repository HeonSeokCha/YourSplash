package com.chs.yoursplash.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey
    val searchQuery: String,
    val lastSearchTime: Long = 0
)
