package data.repository

import com.chs.yoursplash.domain.repository.SettingRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingRepository {


    override suspend fun putString(
        keyName: String,
        value: String
    ) {
        dataStore.edit { preference ->
            preference[stringPreferencesKey(keyName)] = value
        }
    }

    override suspend fun getString(
        keyName: String,
        defaultValue: String
    ): String = dataStore.data
        .catch { e ->
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[stringPreferencesKey(keyName)] ?: defaultValue
        }.first()
}