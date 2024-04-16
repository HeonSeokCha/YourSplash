package com.chs.yoursplash.presentation.browse.photo_detail

import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.data.db.entity.PhotoSaveEntity
import com.chs.yoursplash.domain.usecase.*
import com.chs.yoursplash.util.Constants
import com.chs.yoursplash.util.DownLoadState
import com.chs.yoursplash.util.PhotoSaveState
import com.chs.yoursplash.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    private val getPhotoRelatedListUseCase: GetPhotoRelatedListUseCase,
    private val getPhotoSaveInfoUseCase: GetPhotoSaveInfoUseCase,
    private val insertPhotoSaveInfoUseCase: InsertPhotoSaveInfoUseCase,
    private val deletePhotoSaveInfoUseCase: DeletePhotoSaveInfoUseCase,
    private val getStringPrefUseCase: GetStringPrefUseCase
) : ViewModel() {

    var state by mutableStateOf(PhotoDetailState())
    private set

    private lateinit var downLoadFileName: String

    init {
        getImageLoadQuality()
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            state = state.copy(
                wallpaperQuality = getStringPrefUseCase(Constants.PREFERENCE_KEY_WALLPAPER_QUALITY).first(),
                loadQuality = getStringPrefUseCase(Constants.PREFERENCE_KEY_LOAD_QUALITY).first().ifEmpty { "Full" }
            )
        }
    }

    fun setPhotoDownloadState(photoState: DownLoadState) {
        when (photoState) {
            DownLoadState.DOWNLOAD_FAILED -> {
                state = state.copy(
                    imageSaveState = PhotoSaveState.NOT_DOWNLOAD
                )
            }
            DownLoadState.DOWNLOAD_SUCCESS -> {
                insertSaveFile(downLoadFileName)
            }
            DownLoadState.DOWNLOADING -> {
                state = state.copy(
                    imageSaveState = PhotoSaveState.DOWNLOADING
                )
            }
        }
    }

    fun getImageDetailInfo(imageId: String) {
        viewModelScope.launch {
            getPhotoDetailUseCase(imageId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        downLoadFileName = "${result.data?.user?.userName}-${result.data?.id}.jpg"
                        state = state.copy(
                            isLoading = false,
                            imageDetailInfo = result.data
                        )
                        checkSaveImage(downLoadFileName)
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

    fun getImageRelatedList(imageId: String) {
        viewModelScope.launch {
            getPhotoRelatedListUseCase(imageId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            imageRelatedList = result.data!!
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

    private suspend fun checkSaveImage(fileName: String) {
        val downloadDir: File = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "YourSplash"
        )

        if (downloadDir.isDirectory) {
            var isRealFileSave: Boolean = false
            val isDbInsertFileInfo: Boolean = getPhotoSaveInfoUseCase(fileName) != null

            downloadDir.listFiles()?.forEach { file ->
                if (file.name == fileName) {
                    isRealFileSave = true
                }
            }
            if (isRealFileSave) { // 실제 파일이 있는데
                if (!isDbInsertFileInfo) {  //DB에 없을 경우
                    insertPhotoSaveInfoUseCase.invoke(
                        PhotoSaveEntity(fileName = fileName)
                    )
                }
                state = state.copy(imageSaveState = PhotoSaveState.DOWNLOADED)
            } else { //실제 파일이 없는데
                if (isDbInsertFileInfo) {  //DB에 있을 경우
                    deletePhotoSaveInfoUseCase(fileName)
                }
            }
        }
    }

    private fun insertSaveFile(fileName: String) { // broadcast를 통해 Success일 경우 호출
        viewModelScope.launch {
            insertPhotoSaveInfoUseCase.invoke(
                PhotoSaveEntity(fileName = fileName)
            )
        }
        state = state.copy(imageSaveState = PhotoSaveState.DOWNLOADED)
    }
}