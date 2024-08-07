package presentation.search

import androidx.paging.PagingData
import domain.model.Photo
import domain.model.UnSplashCollection
import domain.model.User
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String? = null,
    val searchPhotoList: Flow<PagingData<Photo>>? = null,
    val searchCollectionList: Flow<PagingData<UnSplashCollection>>? = null,
    val searchUserList: Flow<PagingData<User>>? = null,
    val loadQuality: String = "Regular",
    val orderBy: String = "relevant",
    val color: String? = null,
    val orientation: String? = null
)
