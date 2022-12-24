package com.chs.yoursplash.domain.usecase

import com.chs.yoursplash.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStringPrefUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(keyName: String): Flow<String> {
        return repository.getString(keyName)
    }
}