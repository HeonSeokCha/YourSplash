package com.chs.yoursplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chs.yoursplash.createDataStore
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.db.YourSplashDatabase
import com.chs.yoursplash.data.db.getDatabaseBuilder
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

@Module
actual class PlatformModule {
    @Single
    actual fun provideDatabase(scope: Scope): YourSplashDatabase = getDatabaseBuilder()

    @Single
    actual fun providePref(scope: Scope): DataStore<Preferences> = createDataStore()

    @Single
    actual fun provideFileManager(scope: Scope): FileManager = FileManager()
}