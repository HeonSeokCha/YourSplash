package com.chs.yoursplash.di

import com.chs.yoursplash.data.repository.PhotoRepositoryImpl
import com.chs.yoursplash.data.repository.SearchRepositoryImpl
import com.chs.yoursplash.data.repository.SettingRepositoryImpl
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.domain.repository.SearchRepository
import com.chs.yoursplash.domain.repository.SettingRepository
import com.chs.yoursplash.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPhotoRepository(
        photoRepositoryImpl: PhotoRepositoryImpl
    ): PhotoRepository

    @Binds
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    abstract fun bindPhotoRepository(
        userRepositoryImpl: SearchRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository

}