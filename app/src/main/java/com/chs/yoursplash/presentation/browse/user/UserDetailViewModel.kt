package com.chs.yoursplash.presentation.browse.user

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.usecase.*
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
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
            val detailInfo = getUserDetailUseCase(userName)
            if (detailInfo.totalPhotos == 0) {
                state = if (detailInfo.totalLikes == 0) {
                    state.copy(
                        userTabLabList = listOf("COLLECT")
                    )
                } else {
                    state.copy(
                        userTabLabList = listOf("LIKES", "COLLECT")
                    )
                }

                state = if (detailInfo.totalCollections == 0) {
                    state.copy(
                        userTabLabList = listOf("LIKES")
                    )
                } else {
                    state.copy(
                        userTabLabList = listOf("LIKES", "COLLECT")
                    )
                }
            }

            if (detailInfo.totalLikes == 0) {

                state = if (detailInfo.totalPhotos == 0) {
                    state.copy(
                        userTabLabList = listOf("COLLECT")
                    )
                } else {
                    state.copy(
                        userTabLabList = listOf("PHOTOS", "COLLECT")
                    )
                }

                state = if (detailInfo.totalCollections == 0) {
                    state.copy(
                        userTabLabList = listOf("PHOTOS")
                    )
                } else {
                    state.copy(
                        userTabLabList = listOf("PHOTOS", "COLLECT")
                    )
                }
            }

            if (detailInfo.totalCollections == 0) {

                state = if (detailInfo.totalPhotos == 0) {
                    state.copy(
                        userTabLabList = listOf("LIKES")
                    )
                } else {
                    state.copy(
                        userTabLabList = listOf("PHOTOS", "LIKES")
                    )
                }

                state = if (detailInfo.totalLikes == 0) {
                    state.copy(
                        userTabLabList = listOf("PHOTOS")
                    )
                } else {
                    state.copy(
                        userTabLabList = listOf("PHOTOS", "LIKES")
                    )
                }
            }

            state = UserDetailState(
                isLoading = false,
                loadQuality = getLoadQualityUseCase(),
                userDetailInfo = detailInfo,
                userDetailPhotoList = getUserPhotoUseCase(userName).cachedIn(viewModelScope),
                userDetailCollection = getUserCollectionUseCase(userName).cachedIn(viewModelScope),
                userDetailLikeList = getUserLikesUseCase(userName).cachedIn(viewModelScope)
            )
        }
    }
}