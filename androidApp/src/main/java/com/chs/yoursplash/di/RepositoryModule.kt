package com.chs.yoursplash.di

import com.chs.yoursplash.data.repository.PhotoRepositoryImpl
import com.chs.yoursplash.data.repository.SearchRepositoryImpl
import com.chs.yoursplash.data.repository.SettingRepositoryImpl
import com.chs.yoursplash.data.repository.UserRepositoryImpl
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.domain.repository.SearchRepository
import com.chs.yoursplash.domain.repository.SettingRepository
import com.chs.yoursplash.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
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
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository

}