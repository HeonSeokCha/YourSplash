package com.chs.yoursplash.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.ViewType
import com.chs.yoursplash.domain.usecase.GetDownloadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetWallPaperQualityUseCase
import com.chs.yoursplash.domain.usecase.GetLoadQualityUseCase
import com.chs.yoursplash.domain.usecase.GetViewTypeUseCase
import com.chs.yoursplash.domain.usecase.PutStringPrefUseCase
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SettingViewModel(
    private val getDownloadQualityUseCase: GetDownloadQualityUseCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase,
    private val getWallPaperQualityUseCase: GetWallPaperQualityUseCase,
    private val putStringPrefUseCase: PutStringPrefUseCase,
    private val getViewTypeUseCase: GetViewTypeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state
        .onStart {
            initObserve()
        }
        .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onEvent(event: SettingIntent) {
        when (event) {
            SettingIntent.ClickDownload -> {
                _state.update {
                    it.copy(
                        selectSettingInfo = Constants.TITLE_DOWNLOAD_QUALITY,
                        selectValue = it.downLoadQualityValue.name,
                        settingList = Constants.LOAD_QUALITY_LIST,
                        showDialog = true
                    )
                }
            }

            SettingIntent.ClickWallpaper -> {
                _state.update {
                    it.copy(
                        selectSettingInfo = Constants.TITLE_WALLPAPER_LOAD_QUALITY,
                        selectValue = it.wallpaperQualityValue.name,
                        settingList = Constants.LOAD_QUALITY_LIST,
                        showDialog = true
                    )
                }
            }

            SettingIntent.ClickLoad -> {
                _state.update {
                    it.copy(
                        selectSettingInfo = Constants.TITLE_LOAD_QUALITY,
                        selectValue = it.loadQualityValue.name,
                        settingList = Constants.LOAD_QUALITY_LIST,
                        showDialog = true
                    )
                }
            }

            SettingIntent.ClickViewType -> {
                _state.update {
                    it.copy(
                        selectSettingInfo = Constants.TITLE_VIEW_TYPE,
                        selectValue = it.viewType.name,
                        settingList = Constants.VIEW_TYPE_LIST,
                        showDialog = true
                    )
                }
            }

            SettingIntent.ClickSave -> {
                if (_state.value.selectSettingInfo == null) return

                putSettingValue(_state.value.selectSettingInfo!!.second, _state.value.selectValue)
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

    private fun initObserve() {
        viewModelScope.launch {
            combine(
                getLoadQualityUseCase(),
                getDownloadQualityUseCase(),
                getWallPaperQualityUseCase(),
                getViewTypeUseCase()
            ) { load, download, wallpaper, viewType ->
                listOf(load, download, wallpaper, viewType)
            }.collect { list ->
                _state.update {
                    it.copy(
                        loadQualityValue = list[0] as LoadQuality,
                        downLoadQualityValue = list[1] as LoadQuality,
                        wallpaperQualityValue = list[2] as LoadQuality,
                        viewType = list[3] as ViewType
                    )
                }
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