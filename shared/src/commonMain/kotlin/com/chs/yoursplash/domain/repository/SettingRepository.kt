package com.chs.yoursplash.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun putString(keyName: String, value: String)
    fun getFlowableString(keyName: String, defaultValue: String): Flow<String>
}