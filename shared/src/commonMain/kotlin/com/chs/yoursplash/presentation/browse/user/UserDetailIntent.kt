package com.chs.yoursplash.presentation.browse.user

sealed interface UserDetailIntent {
    data class ChangeTabIndex(val idx: Int) : UserDetailIntent
    data class ClickPhoto(val id: String) : UserDetailIntent
    data class ClickUser(val name: String) : UserDetailIntent
    data class ClickCollect(val id: String) : UserDetailIntent
    data object ClickClose : UserDetailIntent
    data class OnError(val message: String?) : UserDetailIntent

    sealed interface Photo : UserDetailIntent {
        data object Loading : Photo
        data object LoadComplete : Photo
        data class OnError(val message: String?) : Photo
    }

    sealed interface Like : UserDetailIntent {
        data object Loading : Like
        data object LoadComplete : Like
        data class OnError(val message: String?) : Like
    }

    sealed interface Collection : UserDetailIntent {
        data object Loading : Collection
        data object LoadComplete : Collection
        data class OnError(val message: String?) : Collection
    }
}