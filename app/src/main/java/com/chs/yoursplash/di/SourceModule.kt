package com.chs.yoursplash.di

import android.content.Context
import androidx.room.Room
import com.chs.yoursplash.data.api.UnSplashService
import com.chs.yoursplash.data.db.YourSplashDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SourceModule {

    @Singleton
    @Provides
    fun providerKtorHttpClient(): UnSplashService {
        return UnSplashService(
            HttpClient(Android) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
            }
        )
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): YourSplashDatabase {
        return Room.databaseBuilder(
            context,
            YourSplashDatabase::class.java,
            "your_splash_db"
        ).build()
    }
}