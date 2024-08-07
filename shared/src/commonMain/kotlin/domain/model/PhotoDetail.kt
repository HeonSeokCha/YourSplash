package domain.model


data class PhotoDetail(
    val id: String,
    val width: Int,
    val height: Int,
    val color: String,
    val blurHash: String,
    val likes: Int,
    val urls: PhotoUrls,
    val description: String?,
    val user: User,
    val exif: Exif,
    val location: PhotoLocation,
    val tags: List<UnSplashTag>,
    val views: Int,
    val downloads: Int
)
