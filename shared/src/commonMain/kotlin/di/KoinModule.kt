package di

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
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

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
