package com.chs.yoursplash.domain.repository

import kotlinx.coroutines.flow.Flow


interface SettingRepository {
    suspend fun putString(keyName: String, value: String)
    suspend fun getFlowableString(keyName: String, defaultValue: String): Flow<String>
    suspend fun getString(keyName: String, defaultValue: String): String
}