package com.chs.yoursplash.presentation.browse.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.*
import util.Constants
import com.chs.yoursplash.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getUserPhotoUseCase: GetUserPhotoUseCase,
    private val getUserLikesUseCase: GetUserLikesUseCase,
    private val getUserCollectionUseCase: GetUserCollectionUseCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    private val userName: String = savedStateHandle[Constants.ARG_KEY_USER_NAME] ?: ""

    var state by mutableStateOf(UserDetailState())
        private set

    init {
        viewModelScope.launch {
            state = UserDetailState(
                loadQuality = getLoadQualityUseCase(),
                userDetailPhotoList = getUserPhotoUseCase(userName).cachedIn(viewModelScope),
                userDetailCollection = getUserCollectionUseCase(userName).cachedIn(viewModelScope),
                userDetailLikeList = getUserLikesUseCase(userName).cachedIn(viewModelScope)
            )
        }
    }

    fun getUserDetailInfo() {
        viewModelScope.launch {
            getUserDetailUseCase(userName).collect { result ->
                state = when (result) {
                    is NetworkResult.Loading -> {
                        state.copy(
                            isLoading = true,
                            isError = false
                        )
                    }

                    is NetworkResult.Success -> {
                        state.copy(
                            isLoading = false,
                            userDetailInfo = result.data
                        )
                    }

                    is NetworkResult.Error -> {
                        state.copy(
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