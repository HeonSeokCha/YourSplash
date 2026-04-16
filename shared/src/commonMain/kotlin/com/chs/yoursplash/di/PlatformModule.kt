package com.chs.yoursplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.db.YourSplashDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
expect class PlatformModule {
    @Single
    expect fun provideDatabase(): YourSplashDatabase
    @Single
    expect fun providePref(): DataStore<Preferences>
    @Single
    expect fun provideFileManager(): FileManager
}