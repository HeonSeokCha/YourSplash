package presentation.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.GetDownloadQualityUseCase
import domain.usecase.GetImageDetailQualityUseCase
import domain.usecase.GetLoadQualityUseCase
import domain.usecase.PutStringPrefUseCase
import util.Constants
import kotlinx.coroutines.launch

class SettingViewModel constructor(
    private val getDownloadQualityUseCase: GetDownloadQualityUseCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase,
    private val getImageDetailQualityUseCase: GetImageDetailQualityUseCase,
    private val putStringPrefUseCase: PutStringPrefUseCase
) : ViewModel() {

    var state by mutableStateOf(SettingState())

    init {
        viewModelScope.launch {
            state = state.copy(
                downLoadQualityValue = getDownloadQualityUseCase(),
                loadQualityValue = getLoadQualityUseCase(),
                wallpaperQualityValue = getImageDetailQualityUseCase()
            )
        }
    }

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
            state = when (key) {
                Constants.PREFERENCE_KEY_LOAD_QUALITY -> {
                    state.copy(loadQualityValue = value)
                }

                Constants.PREFERENCE_KEY_DOWNLOAD_QUALITY -> {
                    state.copy(downLoadQualityValue = value)
                }

                Constants.PREFERENCE_KEY_WALLPAPER_QUALITY -> {
                    state.copy(wallpaperQualityValue = value)
                }

                else -> state
            }
        }
    }
}