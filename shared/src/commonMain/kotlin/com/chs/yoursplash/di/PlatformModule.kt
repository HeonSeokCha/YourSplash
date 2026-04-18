package com.chs.yoursplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.db.YourSplashDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

//class PlatformModule {
//    @Single
//    fun provideDatabase(scope : org.koin.core.scope.Scope): YourSplashDatabase
//    @Single
//    fun providePref(scope : org.koin.core.scope.Scope): DataStore<Preferences>
//    @Single
//    fun provideFileManager(scope : org.koin.core.scope.Scope): FileManager
//}