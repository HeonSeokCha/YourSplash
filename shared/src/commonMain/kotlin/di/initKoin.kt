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
import domain.usecase.InsertSearchHistoryUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.bottom.collection.CollectionViewModel
import presentation.bottom.home.HomeViewModel
import presentation.main.MainViewModel

val appModule = module {
    singleOf(::PhotoRepositoryImpl).bind<PhotoRepository>()
    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
    singleOf(::SettingRepositoryImpl).bind<SettingRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()

    factory { GetHomePhotosUseCase(get()) }
    factory { GetHomeCollectionsUseCase(get()) }
    factory { InsertSearchHistoryUseCase(get())}
    factory { DeleteSearchHistoryUseCase(get())}
    factory { GetRecentSearchHistoryUseCase(get())}
    factory { GetLoadQualityUseCase(get())}
    factory { UnSplashService(get()) }

    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CollectionViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule, provideHttpClientModule)
    }
}
