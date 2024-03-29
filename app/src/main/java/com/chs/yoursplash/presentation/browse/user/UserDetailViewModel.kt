package com.chs.yoursplash.presentation.browse.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.chs.yoursplash.domain.model.Photo
import com.chs.yoursplash.domain.model.UnSplashCollection
import com.chs.yoursplash.domain.usecase.*
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getUserPhotoUseCase: GetUserPhotoUseCase,
    private val getUserLikesUseCase: GetUserLikesUseCase,
    private val getUserCollectionUseCase: GetUserCollectionUseCase,
    private val getStringPrefUseCase: GetStringPrefUseCase
) : ViewModel() {

    var state by mutableStateOf(UserDetailState())

    init {
        getImageLoadQuality()
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            state = state.copy(
                loadQuality = getStringPrefUseCase(Constants.PREFERENCE_KEY_LOAD_QUALITY).first()
            )
        }
    }

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
                            userDetailInfo = result.data,
                            userTabLabList = result.data.run {
                                val tempTabList: MutableList<String> = mutableListOf()
                                this?.let {
                                    if (it.totalPhotos != 0) {
                                        tempTabList.add("PHOTOS")
                                    }
                                    if (it.totalCollections!= 0) {
                                        tempTabList.add("COLLECTIONS")
                                    }
                                    if (it.totalLikes != 0) {
                                        tempTabList.add("LIKES")
                                    }
                                }
                                tempTabList
                            }
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
        state = state.copy(
            userDetailPhotoList = getUserPhotoUseCase(userName).cachedIn(viewModelScope)
        )
    }

    fun getUserDetailLikes(userName: String) {
        state = state.copy(
            userDetailLikeList = getUserLikesUseCase(userName).cachedIn(viewModelScope)
        )
    }

    fun getUserDetailCollections(userName: String) {
        state = state.copy(
            userDetailCollection = getUserCollectionUseCase(userName).cachedIn(viewModelScope)
        )
    }
}