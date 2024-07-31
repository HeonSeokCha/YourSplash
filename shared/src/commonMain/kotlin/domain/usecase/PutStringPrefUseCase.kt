package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SettingRepository
import javax.inject.Inject

class PutStringPrefUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(
        keyName: String,
        value: String
    ) {
        repository.putString(keyName, value)
    }
}