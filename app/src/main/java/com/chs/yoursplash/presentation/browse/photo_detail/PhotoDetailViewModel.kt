package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.*
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val getPhotoRelatedListUseCase: GetPhotoRelatedListUseCase,
    private val loadQualityUseCase: GetLoadQualityUseCase,
    private val imageDetailQualityUseCase: GetImageDetailQualityUseCase,
    private val getPhotoSaveInfoUseCase: GetPhotoSaveInfoUseCase,
    private val insertPhotoSaveInfoUseCase: InsertPhotoSaveInfoUseCase
) : ViewModel() {

    private val imageId: String = savedStateHandle[Constants.ARG_KEY_PHOTO_ID] ?: ""

    val state = flow {
        emit(PhotoDetailState(isLoading = true))

        emit(
            PhotoDetailState(
                isLoading = false,
                wallpaperQuality = imageDetailQualityUseCase(),
                loadQuality = loadQualityUseCase(),
                imageDetailInfo = getPhotoDetailUseCase(imageId),
                imageRelatedList = getPhotoRelatedListUseCase(imageId)
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        PhotoDetailState()
    )
}