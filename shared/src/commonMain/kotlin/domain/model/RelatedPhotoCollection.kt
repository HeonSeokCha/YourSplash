package domain.model

data class RelatedPhotoCollection(
    val total: Int,
    val result: List<UnSplashCollection> = listOf()
)
