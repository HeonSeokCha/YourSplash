package domain.usecase

import com.chs.yoursplash.domain.repository.SettingRepository
import util.Constants

class GetDownloadQualityUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): String {
        return repository.getString(
            Constants.PREFERENCE_KEY_DOWNLOAD_QUALITY,
            Constants.QUALITY_LIST[2]
        )
    }
}