package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.LoadQuality
import com.chs.yoursplash.domain.repository.SettingRepository
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLoadQualityUseCase(
    private val repository: SettingRepository
) {
    operator fun invoke(): Flow<LoadQuality> {
        return repository.getFlowableString(
            Constants.PREFERENCE_KEY_LOAD_QUALITY,
            LoadQuality.Regular.name
        ).map { LoadQuality.valueOf(it) }
    }
}
