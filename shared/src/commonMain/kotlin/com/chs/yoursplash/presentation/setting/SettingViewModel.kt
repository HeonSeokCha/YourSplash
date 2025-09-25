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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(
    getDownloadQualityUseCase: GetDownloadQualityUseCase,
    getLoadQualityUseCase: GetLoadQualityUseCase,
    getImageDetailQualityUseCase: GetImageDetailQualityUseCase,
    private val putStringPrefUseCase: PutStringPrefUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = combine(
        getDownloadQualityUseCase(),
        getLoadQualityUseCase(),
        getImageDetailQualityUseCase(),
        _state,
    ) { downloadQuality, loadQuality, wallpaperQuality, currentState ->
        currentState.copy(
            downLoadQualityValue = downloadQuality,
            loadQualityValue = loadQuality,
            wallpaperQualityValue = wallpaperQuality
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onEvent(event: SettingIntent) {
        when (event) {
            SettingIntent.ClickDownload -> {
                _state.update {
                    it.copy(
                        selectSettingTitle = "Download Quality",
                        selectValue = it.downLoadQualityValue,
                        showDialog = true
                    )
                }
            }

            SettingIntent.ClickWallpaper -> {
                _state.update {
                    it.copy(
                        selectSettingTitle = "Wallpaper Quality",
                        selectValue = it.wallpaperQualityValue,
                        showDialog = true
                    )
                }
            }

            SettingIntent.ClickLoad -> {
                _state.update {
                    it.copy(
                        selectSettingTitle = "Load Quality",
                        selectValue = it.loadQualityValue,
                        showDialog = true
                    )
                }
            }

            SettingIntent.ClickSave -> {
                putSettingValue(_state.value.selectSettingTitle, _state.value.selectValue.name)
                _state.update { it.copy(showDialog = false) }
            }

            SettingIntent.CloseDialog -> {
                _state.update { it.copy(showDialog = false) }
            }

            is SettingIntent.SelectValue -> {
                _state.update { it.copy(selectValue = event.value) }
            }
        }
    }

    private fun putSettingValue(
        key: String,
        value: String
    ) {
        viewModelScope.launch {
            putStringPrefUseCase(key, value)
        }
    }
}