package domain.usecase

import com.chs.yoursplash.domain.repository.SettingRepository

class PutStringPrefUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(
        keyName: String,
        value: String
    ) {
        repository.putString(keyName, value)
    }
}