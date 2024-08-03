package presentation.bottom.collection

import androidx.paging.PagingData
import domain.model.UnSplashCollection
import kotlinx.coroutines.flow.Flow

data class CollectionState(
    val collectionList: Flow<PagingData<UnSplashCollection>>? = null,
    val loadQuality: String = "Regular",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
