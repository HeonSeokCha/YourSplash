package com.chs.yoursplash.domain.repository


interface SettingRepository {
    suspend fun putString(keyName: String, value: String)
    suspend fun getString(keyName: String): String
}