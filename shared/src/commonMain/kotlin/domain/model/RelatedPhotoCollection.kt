package domain.model

import com.chs.yoursplash.domain.model.UnSplashCollection

data class RelatedPhotoCollection(
    val total: Int,
    val result: List<UnSplashCollection> = listOf()
)
