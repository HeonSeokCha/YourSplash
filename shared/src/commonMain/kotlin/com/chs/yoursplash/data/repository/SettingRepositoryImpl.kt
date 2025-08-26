package com.chs.yoursplash.data.repository

import com.chs.yoursplash.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class SettingRepositoryImpl(
    private val dataStore: DataStorePrefManager
) : SettingRepository {

    override suspend fun putString(
        keyName: String,
        value: String
    ) = dataStore.putData(keyName, value)

    override suspend fun getFlowableString(
        keyName: String,
        defaultValue: String
    ): Flow<String> = dataStore.getData(keyName = keyName, defaultValue = defaultValue).distinctUntilChanged()
}