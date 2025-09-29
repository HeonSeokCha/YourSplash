package com.chs.yoursplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chs.yoursplash.createDataStore
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.db.YourSplashDatabase
import com.chs.yoursplash.data.db.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<YourSplashDatabase> { getDatabaseBuilder() }
    single<DataStore<Preferences>> { createDataStore() }
    single<FileManager> { FileManager() }
}
