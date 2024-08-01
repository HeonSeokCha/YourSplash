package domain.model

import domain.model.PhotoUrls

data class UserPhotos(
    val id: String,
    val blurHash: String,
    val urls: PhotoUrls
)
