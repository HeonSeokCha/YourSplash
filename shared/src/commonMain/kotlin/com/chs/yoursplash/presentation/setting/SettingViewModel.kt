package com.chs.yoursplash.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetDownloadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetImageDetailQualityUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.PutStringPrefUseCase
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(
    private val getDownloadQualityUseCase: GetDownloadQualityUseCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase,
    private val getImageDetailQualityUseCase: GetImageDetailQualityUseCase,
    private val putStringPrefUseCase: PutStringPrefUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state
        .onStart {
            _state.update {
                it.copy(
                    downLoadQualityValue = getDownloadQualityUseCase(),
                    loadQualityValue = getLoadQualityUseCase(),
                    wallpaperQualityValue = getImageDetailQualityUseCase()
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SettingState()
        )

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.PutSettingValue -> {
                putSettingValue(
                    key = event.key,
                    value = event.value
                )
            }
        }
    }

    private fun putSettingValue(
        key: String,
        value: String
    ) {
        viewModelScope.launch {
            putStringPrefUseCase(key, value)
            _state.update {
                when (key) {
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