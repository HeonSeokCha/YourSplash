package com.chs.yoursplash.presentation.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetStringPrefUseCase
import com.chs.yoursplash.domain.usecase.PutStringPrefUseCase
import com.chs.yoursplash.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getStringPrefUseCase: GetStringPrefUseCase,
    private val putStringPrefUseCase: PutStringPrefUseCase
) : ViewModel() {

    var state by mutableStateOf(SettingState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                downLoadQualityValue = getStringPrefUseCase(Constants.PREFERENCE_KEY_DOWNLOAD_QUALITY).first()
                    .ifEmpty { "Regular" },
                loadQualityValue = getStringPrefUseCase(Constants.PREFERENCE_KEY_LOAD_QUALITY).first()
                    .ifEmpty { "Full" },
                wallpaperQualityValue = getStringPrefUseCase(Constants.PREFERENCE_KEY_WALLPAPER_QUALITY).first()
                    .ifEmpty { "Full" }
            )
        }
    }

    fun putSettingPreference(title: String, value: String) {
        viewModelScope.launch {
            putStringPrefUseCase(title, value)
            when (title) {
                Constants.PREFERENCE_KEY_LOAD_QUALITY-> {
                    state = state.copy(loadQualityValue = value)
                }
                Constants.PREFERENCE_KEY_DOWNLOAD_QUALITY -> {
                    state = state.copy(downLoadQualityValue = value)
                }
                Constants.PREFERENCE_KEY_WALLPAPER_QUALITY -> {
                    state = state.copy(wallpaperQualityValue = value)
                }
                else -> Unit
            }
        }
    }
}