package com.chs.yoursplash.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetDownloadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetImageDetailQualityUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.PutStringPrefUseCase
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getDownloadQualityUseCase: GetDownloadQualityUseCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase,
    private val getImageDetailQualityUseCase: GetImageDetailQualityUseCase,
    private val putStringPrefUseCase: PutStringPrefUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<SettingState> = MutableStateFlow(SettingState())
    val state: StateFlow<SettingState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    downLoadQualityValue = getDownloadQualityUseCase(),
                    loadQualityValue = getLoadQualityUseCase(),
                    wallpaperQualityValue = getImageDetailQualityUseCase()
                )
            }
        }
    }

    fun putSettingPreference(
        title: String,
        value: String
    ) {
        viewModelScope.launch {
            putStringPrefUseCase(title, value)
            _state.update {
                when (title) {
                    Constants.PREFERENCE_KEY_LOAD_QUALITY -> {
                        it.copy(loadQualityValue = value)
                    }

                    Constants.PREFERENCE_KEY_DOWNLOAD_QUALITY -> {
                        it.copy(downLoadQualityValue = value)
                    }

                    Constants.PREFERENCE_KEY_WALLPAPER_QUALITY -> {
                        it.copy(wallpaperQualityValue = value)
                    }

                    else -> it
                }
            }
        }
    }
}