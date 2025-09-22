package com.chs.yoursplash.presentation.browse.user

sealed interface UserDetailEffect {
    data class NavigatePhotoDetail(val id: String) : UserDetailEffect
    data class NavigateCollectionDetail(val id: String) : UserDetailEffect
    data class NavigateUserDetail(val name: String) : UserDetailEffect
    data class ShowToast(val text: String) : UserDetailEffect
    data object Close : UserDetailEffect
}