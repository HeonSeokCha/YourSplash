package com.chs.yoursplash.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity(tableName = "search_history")
data class SearchHistoryEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey
    val searchQuery: String,
    val lastSearchTime: Long = Clock.System.now().toEpochMilliseconds()
)
