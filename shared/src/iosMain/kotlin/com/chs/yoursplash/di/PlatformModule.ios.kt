package com.chs.yoursplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chs.yoursplash.createDataStore
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.db.YourSplashDatabase
import com.chs.yoursplash.data.db.getDatabaseBuilder
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
actual class PlatformModule {
    @Single
    fun provideDatabase(): YourSplashDatabase = getDatabaseBuilder()

    @Single
    fun providePref(): DataStore<Preferences> = createDataStore()

    @Single
    fun provideFileManager(): FileManager = FileManager()
}