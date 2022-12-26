package com.chs.yoursplash.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.chs.yoursplash.domain.repository.SettingRepository
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCE_NAME)

class SettingRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingRepository {

    private val dataStore = context.dataStore

    override suspend fun putString(keyName: String, value: String) {
        dataStore.edit { preference ->
            preference[stringPreferencesKey(name = keyName)] = value
        }
    }

    override suspend fun getString(keyName: String): Flow<String> = dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else { throw e }
        }.map { preferences ->
            preferences[stringPreferencesKey(name = keyName)] ?: ""
        }
}