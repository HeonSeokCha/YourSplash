package com.chs.yoursplash.presentation.bottom.collection



sealed interface CollectionEffect {
    data class NavigateCollectionDetail(val id: String) : CollectionEffect
    data class NavigateUserDetail(val name: String) : CollectionEffect
    data class ShowToast(val message: String) : CollectionEffect
}