package com.chs.yoursplash.data.db.entity

import androidx.room.Entity

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    val insertTime: Long = System.currentTimeMillis(),
    val searchQuery: String
)
