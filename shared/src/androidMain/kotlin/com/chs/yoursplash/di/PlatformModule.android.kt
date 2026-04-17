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
actual class PlatformModule(
    private val context: Context
) {
    @Single
    actual fun provideDatabase(): YourSplashDatabase = getDatabaseBuilder(context)

    @Single
    actual fun providePref(): DataStore<Preferences> = createDataStores(context)

    @Single
    actual fun provideFileManager(): FileManager = FileManager(context)
}