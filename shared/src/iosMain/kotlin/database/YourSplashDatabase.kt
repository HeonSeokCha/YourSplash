package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.db.YourSplashDatabase
import data.db.instantiateImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): YourSplashDatabase {
    val dbFile = "${NSHomeDirectory()}/your_splash.db"
    return Room.databaseBuilder<YourSplashDatabase>(
        name = dbFile,
        factory = { YourSplashDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
