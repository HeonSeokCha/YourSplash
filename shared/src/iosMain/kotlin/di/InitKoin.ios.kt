package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import createDataStore
import data.db.YourSplashDatabase
import data.db.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<YourSplashDatabase> { getDatabaseBuilder() }
    single<DataStore<Preferences>> { createDataStore() }
}
