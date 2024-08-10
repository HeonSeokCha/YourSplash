package di

import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoTagListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.bottom.collection.CollectionViewModel
import presentation.bottom.home.HomeViewModel
import presentation.browse.user.UserDetailViewModel
import presentation.main.MainViewModel
import presentation.search.SearchResultViewModel
import presentation.setting.SettingViewModel

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