package com.chs.yoursplash.di

import com.chs.yoursplash.data.repository.SettingRepositoryImpl
import com.chs.yoursplash.data.repository.SplashRepositoryImpl
import com.chs.yoursplash.domain.repository.SettingRepository
import com.chs.yoursplash.domain.repository.SplashRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSplashRepository(
        splashRepositoryImpl: SplashRepositoryImpl
    ): SplashRepository

    @Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository

}