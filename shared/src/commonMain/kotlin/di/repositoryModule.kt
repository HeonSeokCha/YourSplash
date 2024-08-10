package di

import com.chs.yoursplash.domain.repository.SettingRepository
import data.repository.PhotoRepositoryImpl
import data.repository.SearchRepositoryImpl
import data.repository.SettingRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.PhotoRepository
import domain.repository.SearchRepository
import domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::PhotoRepositoryImpl).bind<PhotoRepository>()
    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
    singleOf(::SettingRepositoryImpl).bind<SettingRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
}
