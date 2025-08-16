package com.chs.yoursplash.domain.model

data class UnSplashCollection(
    val id: String,
    val title: String,
    val totalPhotos: Int,
    val user: User,
    val previewPhotos: List<RelatedCollectionPreview>?
)
