package com.chs.yoursplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.db.YourSplashDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

expect class PlatformModule {
    fun provideDatabase(): YourSplashDatabase
    fun providePref(): DataStore<Preferences>
    fun provideFileManager(): FileManager
}