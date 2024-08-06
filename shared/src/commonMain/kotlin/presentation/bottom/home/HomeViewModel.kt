package presentation.bottom.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import domain.usecase.GetHomePhotosUseCase
import domain.usecase.GetLoadQualityUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomePhotosUseCase: GetHomePhotosUseCase,
//    private val loadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            state = HomeState(
                isLoading = false,
//                loadQuality = loadQualityUseCase(),
                pagingImageList = getHomePhotosUseCase().cachedIn(this)
            )
        }
    }
}