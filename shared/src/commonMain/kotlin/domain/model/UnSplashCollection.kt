package domain.model

import domain.model.RelatedCollectionPreview

data class UnSplashCollection(
    val id: String,
    val title: String,
    val totalPhotos: Int,
    val unSplashTags: List<UnSplashTag>,
    val user: User,
    val previewPhotos: List<RelatedCollectionPreview>?
)
