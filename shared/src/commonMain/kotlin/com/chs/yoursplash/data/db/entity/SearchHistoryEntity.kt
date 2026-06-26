package com.chs.yoursplash.data.db.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity(tableName = "search_history")
data class SearchHistoryEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey
    val searchQuery: String,
    val lastSearchTime: Long = Clock.System.now().toEpochMilliseconds()
)
