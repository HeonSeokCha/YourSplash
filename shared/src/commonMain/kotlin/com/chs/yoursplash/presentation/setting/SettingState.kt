package com.chs.yoursplash.presentation.setting

import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.model.ViewType

data class SettingState(
    val loadQualityValue: LoadQuality = LoadQuality.Regular,
    val downLoadQualityValue: LoadQuality = LoadQuality.Regular,
    val wallpaperQualityValue: LoadQuality = LoadQuality.Regular,
    val viewType: ViewType = ViewType.Normal,
    val showDialog: Boolean = false,
    val selectSettingInfo: Pair<String, String>? = null,
    val settingList: List<String> = emptyList(),
    val selectValue: String = ""
)