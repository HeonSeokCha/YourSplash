package domain.model

import com.chs.yoursplash.domain.model.User

data class Photo(
    val id: String,
    val color: String,
    val blurHash: String?,
    val width: Int,
    val height: Int,
    val urls: PhotoUrls,
    val user: User
)