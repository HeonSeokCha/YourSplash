package com.chs.yoursplash.presentation.setting

import com.chs.yoursplash.domain.model.LoadQuality

sealed class SettingEvent {
    data object ClickLoad : SettingEvent()
    data object ClickDownload : SettingEvent()
    data object ClickWallpaper : SettingEvent()
    data object ClickSave : SettingEvent()
    data class SelectValue(val value: LoadQuality) : SettingEvent()
    data object CloseDialog : SettingEvent()
}