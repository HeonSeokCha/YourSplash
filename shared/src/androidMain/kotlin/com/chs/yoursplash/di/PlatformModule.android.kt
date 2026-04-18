package com.chs.yoursplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chs.yoursplash.createDataStores
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.db.YourSplashDatabase
import com.chs.yoursplash.data.db.getDatabaseBuilder
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class AndroidModule {
    @Single
    fun provideDatabase(scope : org.koin.core.scope.Scope): YourSplashDatabase = getDatabaseBuilder(scope)

    @Single
    fun providePref(scope : org.koin.core.scope.Scope): DataStore<Preferences> = createDataStores(scope)

    @Single
    fun provideFileManager(scope : org.koin.core.scope.Scope): FileManager = FileManager(scope)
}