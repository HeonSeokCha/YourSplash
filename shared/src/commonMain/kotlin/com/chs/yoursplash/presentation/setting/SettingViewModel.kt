package com.chs.yoursplash.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.usecase.GetDownloadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetImageDetailQualityUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.PutStringPrefUseCase
import com.chs.yoursplash.util.Constants
import io.ktor.util.Hash.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
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
            combine(
                getDownloadQualityUseCase(),
                getLoadQualityUseCase(),
                getImageDetailQualityUseCase()
            ) { list ->
                _state.update {
                    it.copy(
                        downLoadQualityValue = list[0],
                        loadQualityValue = list[1],
                        wallpaperQualityValue = list[2]
                    )
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                SettingState()
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SettingState()
        )

    fun onEvent(event: SettingEvent) {
        when (event) {
            SettingEvent.ClickDownload -> {
                _state.update { it.copy(selectSettingTitle = Constants.PREFERENCE_KEY_DOWNLOAD_QUALITY) }
            }
            SettingEvent.ClickWallpaper -> {
                _state.update { it.copy(selectSettingTitle = Constants.PREFERENCE_KEY_WALLPAPER_QUALITY) }
            }
            SettingEvent.ClickLoad -> {
                _state.update { it.copy(selectSettingTitle = Constants.PREFERENCE_KEY_LOAD_QUALITY) }
            }
            SettingEvent.ClickSave -> {
                putSettingValue(_state.value.selectSettingTitle, _state.value.selectValue.name)
                _state.update { it.copy(showDialog = false) }
            }

            SettingEvent.CloseDialog -> {
                _state.update { it.copy(showDialog = false) }
            }

            is SettingEvent.SelectValue -> {
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