package com.chs.yoursplash.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.chs.yoursplash.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingRepository {


    override suspend fun <T> putData(
        key: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { preference ->
            preference[key] = value
        }
    }

    override suspend fun <T> getData(
        key: Preferences.Key<T>,
        defaultValue: T
    ): T = dataStore.data
        .catch { e ->
            Log.e("SettingRepositoryImpl", "Catch SettingRepositoryImpl Exception ${e.message}")
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[key] ?: defaultValue
        }.first()
}