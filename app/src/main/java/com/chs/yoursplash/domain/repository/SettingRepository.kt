package com.chs.yoursplash.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun putString(keyName: String, value: String)
    suspend fun getString(keyName: String): Flow<String>
}