package com.chs.yoursplash.presentation.search


sealed interface SearchEffect {
    data object NavigateBack : SearchEffect
    data class NavigateCollectionDetail(val id: String) : SearchEffect
    data class NavigatePhotoDetail(val id: String) : SearchEffect
    data class NavigateUserDetail(val name: String) : SearchEffect
    data class ShowToast(val message: String) : SearchEffect
}