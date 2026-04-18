package com.chs.yoursplash

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chs.yoursplash.data.DATA_STORE_FILE_NAME
import com.chs.yoursplash.data.createDataStore

fun createDataStores(scope : org.koin.core.scope.Scope): DataStore<Preferences> {
    val context: Context = scope.get()
    return createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}