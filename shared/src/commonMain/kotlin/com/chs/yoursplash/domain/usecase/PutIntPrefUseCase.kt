package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SettingRepository

class PutIntPrefUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(
        keyName: String,
        value: Int
    ) {
        repository.putInt(keyName, value)
    }
}