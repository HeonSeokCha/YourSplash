package domain.usecase

import com.chs.yoursplash.domain.repository.SettingRepository
import util.Constants

class GetImageDetailQualityUseCase(
    private val repository: SettingRepository
) {
    suspend operator fun invoke(): String {
        return repository.getString(
            Constants.PREFERENCE_KEY_WALLPAPER_QUALITY,
            Constants.QUALITY_LIST[2]
        )
    }
}