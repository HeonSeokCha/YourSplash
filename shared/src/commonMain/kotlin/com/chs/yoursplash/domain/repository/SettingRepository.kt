package com.chs.yoursplash.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun putString(keyName: String, value: String)
    suspend fun putInt(keyName: String, value: Int)
    fun getFlowableString(keyName: String, defaultValue: String): Flow<String>
    suspend fun getInt(keyName: String, defaultValue: Int): Int
}