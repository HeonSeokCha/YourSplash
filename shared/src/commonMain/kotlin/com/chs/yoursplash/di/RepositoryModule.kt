package com.chs.yoursplash.di

import com.chs.yoursplash.data.repository.PhotoRepositoryImpl
import com.chs.yoursplash.data.repository.SearchRepositoryImpl
import com.chs.yoursplash.data.repository.SettingRepositoryImpl
import com.chs.yoursplash.data.repository.UserRepositoryImpl
import com.chs.yoursplash.domain.repository.PhotoRepository
import com.chs.yoursplash.domain.repository.SearchRepository
import com.chs.yoursplash.domain.repository.SettingRepository
import com.chs.yoursplash.domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::PhotoRepositoryImpl).bind<PhotoRepository>()
    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
    singleOf(::SettingRepositoryImpl).bind<SettingRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
}
