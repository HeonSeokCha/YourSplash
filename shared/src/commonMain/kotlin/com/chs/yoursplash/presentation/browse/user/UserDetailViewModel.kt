package com.chs.yoursplash.presentation.browse.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.BrowseInfo
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.domain.usecase.GetUserCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetUserDetailUseCase
import com.chs.yoursplash.domain.usecase.GetUserLikesUseCase
import com.chs.yoursplash.domain.usecase.GetUserPhotoUseCase
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    getUserPhotoUseCase: GetUserPhotoUseCase,
    getUserLikesUseCase: GetUserLikesUseCase,
    getUserCollectionUseCase: GetUserCollectionUseCase,
) : ViewModel() {

    private val userName: String = savedStateHandle[Constants.ARG_KEY_USER_NAME] ?: ""
    private val _state = MutableStateFlow(UserDetailState())
    val state = _state
        .onStart {
            getUserDetailInfo()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _effect: Channel<UserDetailEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    val photoPaging = getUserPhotoUseCase(userName).cachedIn(viewModelScope)
    val likePaging = getUserLikesUseCase(userName).cachedIn(viewModelScope)
    val collectPaging = getUserCollectionUseCase(userName).cachedIn(viewModelScope)

    fun handleIntent(intent: UserDetailIntent) {
        when (intent) {
            is UserDetailIntent.OnError -> _effect.trySend(UserDetailEffect.ShowToast(intent.message ?: ""))
            is UserDetailIntent.ChangeTabIndex -> {
                _state.update { it.copy(selectIdx = intent.idx) }
            }
            UserDetailIntent.Collection.LoadComplete -> _state.update { it.copy(isCollectLoading = false) }
            UserDetailIntent.Collection.Loading -> _state.update { it.copy(isCollectLoading = true) }
            is UserDetailIntent.Collection.OnError -> _effect.trySend(UserDetailEffect.ShowToast(intent.message ?: ""))

            UserDetailIntent.Photo.LoadComplete -> _state.update { it.copy(isPhotoLoading = false) }
            UserDetailIntent.Photo.Loading -> _state.update { it.copy(isPhotoLoading = true) }
            is UserDetailIntent.Photo.OnError -> _effect.trySend(UserDetailEffect.ShowToast(intent.message ?: ""))

            UserDetailIntent.Like.LoadComplete -> _state.update { it.copy(isLikeLoading = false) }
            UserDetailIntent.Like.Loading -> _state.update { it.copy(isLikeLoading = true) }
            is UserDetailIntent.Like.OnError -> _effect.trySend(UserDetailEffect.ShowToast(intent.message ?: ""))

            UserDetailIntent.ClickClose -> _effect.trySend(UserDetailEffect.Close)
            is UserDetailIntent.ClickCollect -> {
                _effect.trySend(UserDetailEffect.NavigateCollectionDetail(intent.id))
            }

            is UserDetailIntent.ClickPhoto -> {
                _effect.trySend(UserDetailEffect.NavigatePhotoDetail(intent.id))

            }
            is UserDetailIntent.ClickUser -> {
                _effect.trySend(UserDetailEffect.NavigateUserDetail(intent.name))
            }
        }
    }

    private fun getUserDetailInfo() {
        viewModelScope.launch {
            getUserDetailUseCase(userName).collect { result ->
                _state.update {
                    when (result) {
                        is NetworkResult.Loading -> {
                            it.copy(isUserDetailLoading = true)
                        }

                        is NetworkResult.Success -> {
                            if (result.data != null) {
                                initTabInfo(result.data)
                            }

                            it.copy(
                                isUserDetailLoading = false,
                                userDetailInfo = result.data
                            )
                        }

                        is NetworkResult.Error -> {
                            it.copy(isUserDetailLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun initTabInfo(info: UserDetail) {
        _state.update {
            it.copy(
                tabList = it.tabList.toMutableList().apply {
                    if (info.totalPhotos == 0) {
                        this.remove("PHOTOS")
                    }
                    if (info.totalLikes == 0) {
                        this.remove("LIKES")
                    }
                    if (info.totalCollections == 0) {
                        this.remove("COLLECTIONS")
                    }
                }
            )
        }
    }
}