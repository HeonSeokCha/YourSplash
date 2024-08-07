package di

import data.db.YourSplashDatabase
import database.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<YourSplashDatabase> { getDatabaseBuilder() }
}
