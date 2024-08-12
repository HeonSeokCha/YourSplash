package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import createDataStores
import data.db.YourSplashDatabase
import data.db.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<YourSplashDatabase> { getDatabaseBuilder(get()) }
    single<DataStore<Preferences>> { createDataStores(get()) }
}