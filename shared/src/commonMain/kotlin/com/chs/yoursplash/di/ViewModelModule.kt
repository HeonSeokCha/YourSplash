package com.chs.yoursplash.di

import com.chs.yoursplash.presentation.bottom.collection.CollectionViewModel
import com.chs.yoursplash.presentation.bottom.photo.PhotoViewModel
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoTagListViewModel
import com.chs.yoursplash.presentation.browse.user.UserDetailViewModel
import com.chs.yoursplash.presentation.main.MainViewModel
import com.chs.yoursplash.presentation.search.SearchResultViewModel
import com.chs.yoursplash.presentation.setting.SettingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::PhotoViewModel)
    viewModelOf(::CollectionViewModel)
    viewModelOf(::SearchResultViewModel)
    viewModelOf(::SettingViewModel)
    viewModelOf(::CollectionDetailViewModel)
    viewModelOf(::PhotoDetailViewModel)
    viewModelOf(::PhotoTagListViewModel)
    viewModelOf(::UserDetailViewModel)
}