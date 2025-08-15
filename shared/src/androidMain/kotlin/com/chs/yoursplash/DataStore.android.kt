package com.chs.yoursplash

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStores(context: Context): DataStore<Preferences> {
    return createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}