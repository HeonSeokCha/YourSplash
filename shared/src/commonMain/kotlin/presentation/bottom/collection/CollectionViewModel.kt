package presentation.bottom.collection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import domain.usecase.GetHomeCollectionsUseCase
import domain.usecase.GetLoadQualityUseCase
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val getHomeCollectionsUseCase: GetHomeCollectionsUseCase,
//    private val loadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    var state by mutableStateOf(CollectionState())
        private set

    init {
        viewModelScope.launch {
            state = CollectionState(
                isLoading = false,
//                loadQuality = loadQualityUseCase(),
                collectionList = getHomeCollectionsUseCase().cachedIn(this)
            )
        }
    }
}