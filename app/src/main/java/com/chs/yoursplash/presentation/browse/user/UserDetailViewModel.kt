package com.chs.yoursplash.presentation.browse.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetUserCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetUserDetailUseCase
import com.chs.yoursplash.domain.usecase.GetUserLikesUseCase
import com.chs.yoursplash.domain.usecase.GetUserPhotoUseCase
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getUserPhotoUseCase: GetUserPhotoUseCase,
    private val getUserLikesUseCase: GetUserLikesUseCase,
    private val getUserCollectionUseCase: GetUserCollectionUseCase
) : ViewModel() {

    var state by mutableStateOf(UserDetailState())

    fun getUserDetail(userName: String) {
        viewModelScope.launch {
            getUserDetailUseCase(userName).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            userDetailInfo = result.data
                        )
                    }
                    is Resource.Error -> {

                        state = state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    fun getUserDetailPhoto(userName: String) {
        viewModelScope.launch {
            getUserPhotoUseCase(userName).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            userDetailPhotoList = result.data!!
                        )
                    }
                    is Resource.Error -> {

                        state = state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    fun getUserDetailLikes(userName: String) {
        viewModelScope.launch {
            getUserLikesUseCase(userName).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            userDetailLikeList = result.data!!
                        )
                    }
                    is Resource.Error -> {

                        state = state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    fun getUserDetailCollections(userName: String) {
        viewModelScope.launch {
            getUserCollectionUseCase(userName).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            userDetailCollection = result.data!!
                        )
                    }
                    is Resource.Error -> {

                        state = state.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }
}