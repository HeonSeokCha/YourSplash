package com.chs.yoursplash.presentation.browse.photo_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetDownloadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetImageDetailQualityUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetPhotoDetailUseCase
import com.chs.yoursplash.domain.usecase.GetPhotoFileUseCase
import com.chs.yoursplash.domain.usecase.GetPhotoRelatedListUseCase
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailEffect
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailEffect.*
import com.chs.yoursplash.presentation.toSettingUrl
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.NetworkResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val getPhotoRelatedListUseCase: GetPhotoRelatedListUseCase,
    private val getDownloadQualityUseCase: GetDownloadQualityUseCase,
    private val getImageDetailQualityUseCase: GetImageDetailQualityUseCase,
    private val getPhotoFileUseCase: GetPhotoFileUseCase
) : ViewModel() {
    private val imageId: String = savedStateHandle[Constants.ARG_KEY_PHOTO_ID] ?: ""
    private var imageDetailJob: Job? = null
    private var relatedListJob: Job? = null
    private var downloadJob: Job? = null

    private val _effect = Channel<PhotoDetailEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var _state = MutableStateFlow(PhotoDetailState())
    val state = _state
        .onStart {
            getImageDetailInfo()
            getImageRelatedList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun handleIntent(intent: PhotoDetailIntent) {
        when (intent) {
            PhotoDetailIntent.ClickClose -> _effect.trySend(PhotoDetailEffect.Close)

            is PhotoDetailIntent.ClickPhoto -> {
                _effect.trySend(NavigatePhotoDetail(intent.id))
            }
            is PhotoDetailIntent.ClickTag -> {
                _effect.trySend(NavigatePhotoTag(intent.name))
            }
            is PhotoDetailIntent.ClickUser -> {
                _effect.trySend(NavigateUserDetail(intent.name))
            }

            is PhotoDetailIntent.ClickDownload -> {
                getPhotoFile(intent.url)
            }
        }
    }

    private fun getImageDetailInfo() {
        imageDetailJob?.cancel()
        imageDetailJob = viewModelScope.launch {
            getPhotoDetailUseCase(imageId).collect { result ->
                _state.update {
                    when (result) {
                        is NetworkResult.Loading -> {
                            it.copy(
                                isDetailLoading = true
                            )
                        }

                        is NetworkResult.Success -> {
                            it.copy(
                                isDetailLoading = false,
                                imageDetailInfo = result.data,
                                loadQualityValue = getImageDetailQualityUseCase().first(),
                                downLoadQualityValue = getDownloadQualityUseCase().first(),
                                wallpaperQualityValue = getImageDetailQualityUseCase().first()
                            )
                        }

                        is NetworkResult.Error -> {
                            _effect.trySend(PhotoDetailEffect.ShowToast(result.message ?: ""))
                            it.copy(isDetailLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun getImageRelatedList() {
        relatedListJob?.cancel()
        relatedListJob = viewModelScope.launch {
            getPhotoRelatedListUseCase(imageId).collect { result ->
                _state.update {
                    when (result) {
                        is NetworkResult.Loading -> {
                            it.copy(isRelatedLoading = true)
                        }

                        is NetworkResult.Success -> {
                            it.copy(
                                isRelatedLoading = false,
                                imageRelatedList = result.data ?: emptyList()
                            )
                        }

                        is NetworkResult.Error -> {
                            _effect.trySend(PhotoDetailEffect.ShowToast(result.message ?: ""))
                            it.copy(isRelatedLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun getPhotoFile(url: String) {
        downloadJob?.cancel()
        downloadJob = viewModelScope.launch {
            getPhotoFileUseCase(
                fileName = imageId,
                url = url
            ).collect { result ->
                _state.update {
                    when (result) {
                        is NetworkResult.Loading -> {
                            it.copy(
                                isFileDownLoading = true,
                            )
                        }

                        is NetworkResult.Success -> {
                            it.copy(
                                isFileDownLoading = false,
                                isFileDownloaded = (result.data ?: false),
                            )
                        }

                        is NetworkResult.Error -> {
                            _effect.trySend(PhotoDetailEffect.ShowToast(result.message ?: ""))
                            it.copy(isFileDownLoading = false)
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        imageDetailJob?.cancel()
        relatedListJob?.cancel()
        downloadJob?.cancel()
        super.onCleared()
    }
}