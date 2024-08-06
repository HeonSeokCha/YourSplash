package presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.DeleteSearchHistoryUseCase
import domain.usecase.GetRecentSearchHistoryUseCase
import domain.usecase.InsertSearchHistoryUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase,
    private val getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase
) : ViewModel() {

    var state: MainState by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            getRecentSearchHistoryUseCase().collect { list ->
                state = state.copy(
                    searchHistory = list
                )
            }
        }
    }

    fun insertSearchHistory(query: String) {
        viewModelScope.launch {
            insertSearchHistoryUseCase(query)
        }
    }


    fun deleteSearchHistory(query: String) {
        viewModelScope.launch {
            deleteSearchHistoryUseCase(query)
        }
    }
}