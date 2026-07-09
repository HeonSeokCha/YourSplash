package com.chs.yoursplash.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.db.YourSplashDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import com.chs.yoursplash.util.Constants.DATA_STORE_FILE_NAME
import kotlinx.coroutines.Dispatchers
import okio.Path.Companion.toPath

@Module
actual class PlatformModule {
    @Single
    fun provideDatabase(context: Context): YourSplashDatabase {
        val dbFile = context.getDatabasePath("your_splash.db")
        return Room.databaseBuilder<YourSplashDatabase>(context, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    @Single
    fun providePref(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath {
            context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath()
        }
    }

    @Single
    fun provideFileManager(context: Context): FileManager = FileManager(context)
}