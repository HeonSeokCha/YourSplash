package com.chs.yoursplash.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room3.Room
import com.chs.yoursplash.data.FileManager
import com.chs.yoursplash.data.createDataStore
import com.chs.yoursplash.data.db.YourSplashDatabase
import com.chs.yoursplash.util.Constants.DATA_STORE_FILE_NAME
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import okio.Path.Companion.toPath
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@Module
actual class PlatformModule {
    @OptIn(ExperimentalForeignApi::class)
    @Single
    fun provideDatabase(): YourSplashDatabase {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        ).run { requireNotNull(this?.path())}

        val dbFile = "$documentDirectory/your_splash.db"
        return Room.databaseBuilder<YourSplashDatabase>(
            name = dbFile
        )
            .setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    @OptIn(ExperimentalForeignApi::class)
    @Single
    fun providePref(): DataStore<Preferences>{
        return PreferenceDataStoreFactory.createWithPath {
            val directory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            (requireNotNull(directory).path + "/${DATA_STORE_FILE_NAME}").toPath()
        }
    }

    @Single
    fun provideFileManager(): FileManager = FileManager()
}