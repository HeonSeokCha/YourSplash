package di

import com.chs.yoursplash.domain.repository.SettingRepository
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoTagListViewModel
import data.api.UnSplashService
import data.repository.PhotoRepositoryImpl
import data.repository.SearchRepositoryImpl
import data.repository.SettingRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.PhotoRepository
import domain.repository.SearchRepository
import domain.repository.UserRepository
import domain.usecase.DeleteSearchHistoryUseCase
import domain.usecase.GetCollectionDetailUseCase
import domain.usecase.GetCollectionPhotoUseCase
import domain.usecase.GetDownloadQualityUseCase
import domain.usecase.GetHomeCollectionsUseCase
import domain.usecase.GetHomePhotosUseCase
import domain.usecase.GetImageDetailQualityUseCase
import domain.usecase.GetLoadQualityUseCase
import domain.usecase.GetPhotoDetailUseCase
import domain.usecase.GetPhotoRelatedListUseCase
import domain.usecase.GetRecentSearchHistoryUseCase
import domain.usecase.GetSearchResultCollectionUseCase
import domain.usecase.GetSearchResultPhotoUseCase
import domain.usecase.GetSearchResultUserUseCase
import domain.usecase.GetUserCollectionUseCase
import domain.usecase.GetUserDetailUseCase
import domain.usecase.GetUserLikesUseCase
import domain.usecase.GetUserPhotoUseCase
import domain.usecase.InsertSearchHistoryUseCase
import domain.usecase.PutStringPrefUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.bottom.collection.CollectionViewModel
import presentation.bottom.home.HomeViewModel
import presentation.browse.user.UserDetailViewModel
import presentation.main.MainViewModel
import presentation.search.SearchResultViewModel
import presentation.setting.SettingViewModel

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
    viewModelOf(::SettingViewModel)
    viewModelOf(::CollectionDetailViewModel)
    viewModelOf(::PhotoDetailViewModel)
    viewModelOf(::PhotoTagListViewModel)
    viewModelOf(::UserDetailViewModel)
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
    singleOf(::GetDownloadQualityUseCase)
    singleOf(::GetLoadQualityUseCase)
    singleOf(::GetImageDetailQualityUseCase)
    singleOf(::PutStringPrefUseCase)
    singleOf(::GetPhotoDetailUseCase)
    singleOf(::GetPhotoRelatedListUseCase)
    singleOf(::GetSearchResultPhotoUseCase)
    singleOf(::GetCollectionDetailUseCase)
    singleOf(::GetCollectionPhotoUseCase)

    singleOf(::GetUserCollectionUseCase)
    singleOf(::GetUserDetailUseCase)
    singleOf(::GetUserPhotoUseCase)
    singleOf(::GetUserLikesUseCase)
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
