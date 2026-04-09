package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.model.ViewType
import com.chs.yoursplash.domain.repository.SettingRepository
import com.chs.yoursplash.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetViewTypeUseCase(
    private val repository: SettingRepository
) {
    operator fun invoke(): Flow<ViewType> {
        return repository.getFlowableString(
            Constants.PREFERENCE_KEY_VIEW_TYPE,
            ViewType.Normal.name
        ).map { ViewType.valueOf(it) }
    }
}