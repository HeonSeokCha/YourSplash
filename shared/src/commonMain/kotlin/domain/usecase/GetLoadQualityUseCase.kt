package domain.usecase

import com.chs.yoursplash.domain.repository.SettingRepository
import util.Constants

class GetLoadQualityUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): String {
        return repository.getString(
            Constants.PREFERENCE_KEY_LOAD_QUALITY,
            Constants.QUALITY_LIST[3]
        )
    }
}
