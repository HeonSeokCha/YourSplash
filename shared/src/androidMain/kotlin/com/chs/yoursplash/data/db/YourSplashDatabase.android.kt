package com.chs.yoursplash.data.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.chs.yoursplash.data.db.YourSplashDatabase
import kotlinx.coroutines.Dispatchers

fun getDatabaseBuilder(scope : org.koin.core.scope.Scope): YourSplashDatabase {
    val context: Context = scope.get()
    val dbFile = context.getDatabasePath("your_splash.db")
    return Room.databaseBuilder<YourSplashDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

