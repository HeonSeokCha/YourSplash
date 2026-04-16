package com.chs.yoursplash.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chs.yoursplash.createDataStores
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.db.YourSplashDatabase
import com.chs.yoursplash.data.db.getDatabaseBuilder
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
actual class PlatformModule {
    @Single
    actual fun provideDatabase(context: Context): YourSplashDatabase = getDatabaseBuilder(context)

    @Single
    actual fun providePref(context: Context): DataStore<Preferences> = createDataStores(context)

    @Single
    actual fun provideFileManager(context: Context): FileManager = FileManager(context)
}