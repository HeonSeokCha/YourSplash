package com.chs.yoursplash.presentation.browse.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.UserDetail
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetUserCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetUserDetailUseCase
import com.chs.yoursplash.domain.usecase.GetUserLikesUseCase
import com.chs.yoursplash.domain.usecase.GetUserPhotoUseCase
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
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

    val photoPaging = getUserPhotoUseCase(userName).cachedIn(viewModelScope)
    val likePaging = getUserLikesUseCase(userName).cachedIn(viewModelScope)
    val collectPaging = getUserCollectionUseCase(userName).cachedIn(viewModelScope)

    private fun getUserDetailInfo() {
        viewModelScope.launch {
            getUserDetailUseCase(userName).collect { result ->
                _state.update {
                    when (result) {
                        is NetworkResult.Loading -> {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }

                        is NetworkResult.Success -> {
                            if (result.data != null) {
                                initTabInfo(result.data)
                            }

                            it.copy(
                                isLoading = false,
                                userDetailInfo = result.data
                            )
                        }

                        is NetworkResult.Error -> {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initTabInfo(info: UserDetail) {
        _state.update {
            it.copy(
                userTabLabList = it.userTabLabList.toMutableList().apply {
                    if (info.totalPhotos == 0) {
                        this.remove("PHOTOS")
                    }
                    if (info.totalLikes == 0) {
                        this.remove("LIKES")
                    }
                    if (info.totalCollections == 0) {
                        this.remove("COLLECT")
                    }
                }
            )
        }
    }
}