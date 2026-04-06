package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SettingRepository
import com.chs.yoursplash.util.Constants

class GetViewTypeUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): Int {
        return repository.getInt(
            Constants.PREFERENCE_KEY_VIEW_TYPE,
            0
        )
    }
}