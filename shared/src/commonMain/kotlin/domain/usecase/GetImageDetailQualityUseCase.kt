package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SettingRepository
import util.Constants
import javax.inject.Inject

class GetImageDetailQualityUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): String {
        return repository.getString(
            Constants.PREFERENCE_KEY_WALLPAPER_QUALITY,
            Constants.QUALITY_LIST[2]
        )
    }
}