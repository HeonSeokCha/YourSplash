package com.chs.yoursplash.presentation.setting

import com.chs.yoursplash.domain.model.LoadQuality

data class SettingState(
    val loadQualityValue: LoadQuality = LoadQuality.Regular,
    val downLoadQualityValue: LoadQuality = LoadQuality.Regular,
    val wallpaperQualityValue: LoadQuality = LoadQuality.Regular,
    val showDialog: Boolean = false,
    val selectSettingInfo: Pair<String, String>? = null,
    val selectValue: LoadQuality = LoadQuality.Regular,
)