package di

import com.chs.yoursplash.domain.repository.SettingRepository
import data.api.UnSplashService
import data.repository.PhotoRepositoryImpl
import data.repository.SearchRepositoryImpl
import data.repository.SettingRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.PhotoRepository
import domain.repository.SearchRepository
import domain.repository.UserRepository
import domain.usecase.DeleteSearchHistoryUseCase
import domain.usecase.GetHomeCollectionsUseCase
import domain.usecase.GetHomePhotosUseCase
import domain.usecase.GetLoadQualityUseCase
import domain.usecase.GetRecentSearchHistoryUseCase
import domain.usecase.GetSearchResultCollectionUseCase
import domain.usecase.GetSearchResultPhotoUseCase
import domain.usecase.GetSearchResultUserUseCase
import domain.usecase.InsertSearchHistoryUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.bottom.collection.CollectionViewModel
import presentation.bottom.home.HomeViewModel
import presentation.main.MainViewModel
import presentation.search.SearchResultViewModel

expect fun platformModule(): Module

val sourceModule = module {
    singleOf(::UnSplashService)
}

val repositoryModule = module {
    singleOf(::PhotoRepositoryImpl).bind<PhotoRepository>()
    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
    singleOf(::SettingRepositoryImpl).bind<SettingRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
}

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CollectionViewModel)
    viewModelOf(::SearchResultViewModel)
}

val useCaseModule = module {
    singleOf(::GetHomePhotosUseCase)
    singleOf(::GetHomeCollectionsUseCase)
    singleOf(::InsertSearchHistoryUseCase)
    singleOf(::DeleteSearchHistoryUseCase)
    singleOf(::GetRecentSearchHistoryUseCase)
    singleOf(::GetLoadQualityUseCase)
    singleOf(::GetSearchResultPhotoUseCase)
    singleOf(::GetSearchResultCollectionUseCase)
    singleOf(::GetSearchResultUserUseCase)
    singleOf(::DeleteSearchHistoryUseCase)
    singleOf(::InsertSearchHistoryUseCase)
    singleOf(::GetRecentSearchHistoryUseCase)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            provideHttpClientModule,
            sourceModule,
            repositoryModule,
            viewModelModule,
            useCaseModule,
            platformModule()
        )
    }
}
