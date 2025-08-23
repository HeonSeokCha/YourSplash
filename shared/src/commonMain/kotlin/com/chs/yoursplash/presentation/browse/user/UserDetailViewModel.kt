package com.chs.yoursplash.presentation.browse.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
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
    private val getUserPhotoUseCase: GetUserPhotoUseCase,
    private val getUserLikesUseCase: GetUserLikesUseCase,
    private val getUserCollectionUseCase: GetUserCollectionUseCase,
) : ViewModel() {

    private val userName: String = savedStateHandle[Constants.ARG_KEY_USER_NAME] ?: ""
    private val _state = MutableStateFlow(UserDetailState())
    val state = _state
        .onStart {
            _state.update {
                it.copy(
                    userDetailPhotoList = getUserPhotoUseCase(userName).cachedIn(viewModelScope),
                    userDetailCollection = getUserCollectionUseCase(userName).cachedIn(viewModelScope),
                    userDetailLikeList = getUserLikesUseCase(userName).cachedIn(viewModelScope)
                )
            }
            getUserDetailInfo()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

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
}