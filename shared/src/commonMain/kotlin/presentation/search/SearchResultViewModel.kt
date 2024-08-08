package presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import domain.usecase.GetLoadQualityUseCase
import domain.usecase.GetSearchResultCollectionUseCase
import domain.usecase.GetSearchResultPhotoUseCase
import domain.usecase.GetSearchResultUserUseCase
import kotlinx.coroutines.launch

class SearchResultViewModel(
    private val searchResultPhotoUseCase: GetSearchResultPhotoUseCase,
    private val searchResultCollectionUseCase: GetSearchResultCollectionUseCase,
    private val searchResultUserUseCase: GetSearchResultUserUseCase,
    private val getLoadQualityUseCase: GetLoadQualityUseCase
) : ViewModel() {

    var state: SearchState by mutableStateOf(SearchState())

    init {
        getImageLoadQuality()
    }

    private fun getImageLoadQuality() {
        viewModelScope.launch {
            state = state.copy(
                loadQuality = getLoadQualityUseCase()
            )
        }
    }

    fun searchResult(query: String) {
        state = state.copy(
            searchPhotoList = searchResultPhotoUseCase(
                query = query,
                orderBy = state.orderBy,
                color = state.color,
                orientation = state.orientation
            ).cachedIn(viewModelScope),
            searchCollectionList = searchResultCollectionUseCase(query).cachedIn(viewModelScope),
            searchUserList = searchResultUserUseCase(query).cachedIn(viewModelScope)
        )
    }
}