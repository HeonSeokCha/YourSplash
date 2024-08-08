package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import createDataStores
import db.getDatabaseBuilder
import data.db.YourSplashDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single<YourSplashDatabase> { getDatabaseBuilder(get()) }
    single<DataStore<Preferences>> { createDataStores(get()) }
}