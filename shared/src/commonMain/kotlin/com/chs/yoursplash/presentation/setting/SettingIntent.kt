package com.chs.yoursplash.presentation.setting

import com.chs.yoursplash.domain.model.LoadQuality

sealed interface SettingIntent {
    data object ClickLoad : SettingIntent
    data object ClickDownload : SettingIntent
    data object ClickWallpaper : SettingIntent
    data object ClickSave : SettingIntent
    data class SelectValue(val value: LoadQuality) : SettingIntent
    data object CloseDialog : SettingIntent
}