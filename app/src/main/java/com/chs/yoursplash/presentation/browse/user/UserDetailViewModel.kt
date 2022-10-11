package com.chs.yoursplash.presentation.browse.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.usecase.GetUserCollectionUseCase
import com.chs.yoursplash.domain.usecase.GetUserDetailUseCase
import com.chs.yoursplash.domain.usecase.GetUserLikesUseCase
import com.chs.yoursplash.domain.usecase.GetUserPhotoUseCase
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    fun getUserDetailPhoto(userName: String): Flow<PagingData<Photo>> {
        return getUserPhotoUseCase(userName).cachedIn(viewModelScope)
    }

    fun getUserDetailLikes(userName: String): Flow<PagingData<Photo>> {
        return getUserLikesUseCase(userName).cachedIn(viewModelScope)
    }

    fun getUserDetailCollections(userName: String): Flow<PagingData<UnSplashCollection>> {
        return getUserCollectionUseCase(userName).cachedIn(viewModelScope)
    }
}