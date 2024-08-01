package domain.model

data class User(
    val id: String,
    val userName: String,
    val name: String,
    val photoProfile: UserProfileImage,
    val photos: List<UserPhotos>
)