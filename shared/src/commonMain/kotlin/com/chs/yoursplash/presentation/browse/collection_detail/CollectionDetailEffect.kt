package com.chs.yoursplash.presentation.browse.collection_detail

sealed interface CollectionDetailEffect {
    data class NavigatePhotoDetail(val id: String) : CollectionDetailEffect
    data class NavigateUserDetail(val name: String) : CollectionDetailEffect
    data class ShowToast(val message: String) : CollectionDetailEffect
    data object Close : CollectionDetailEffect
}