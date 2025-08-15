package com.chs.yoursplash.di

import com.chs.yoursplash.domain.usecase.DeleteSearchHistoryUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionDetailUseCase
import com.chs.yoursplash.domain.usecase.GetCollectionPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetDownloadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetHomeCollectionsUseCase
import com.chs.yoursplash.domain.usecase.GetHomePhotosUseCase
import com.chs.yoursplash.domain.usecase.GetImageDetailQualityUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetPhotoDetailUseCase
import com.chs.yoursplash.domain.usecase.GetPhotoRelatedListUseCase
import com.chs.yoursplash.domain.usecase.GetRecentSearchHistoryUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultPhotoUseCase
import com.chs.yoursplash.domain.usecase.GetSearchResultUserUseCase
import com.chs.yoursplash.domain.usecase.GetUserCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetUserDetailUseCase
import com.chs.yoursplash.domain.usecase.GetUserLikesUseCase
import com.chs.yoursplash.domain.usecase.GetUserPhotoUseCase
import com.chs.yoursplash.domain.usecase.InsertSearchHistoryUseCase
import com.chs.yoursplash.domain.usecase.PutStringPrefUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

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
