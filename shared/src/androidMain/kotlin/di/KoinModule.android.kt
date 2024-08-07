package di

import db.getDatabaseBuilder
import data.db.YourSplashDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single<YourSplashDatabase> { getDatabaseBuilder(get()) }
}