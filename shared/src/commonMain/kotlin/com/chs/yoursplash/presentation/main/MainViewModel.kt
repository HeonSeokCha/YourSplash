package com.chs.yoursplash.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chs.yoursplash.domain.usecase.DeleteSearchHistoryUseCase
import com.chs.yoursplash.domain.usecase.GetRecentSearchHistoryUseCase
import com.chs.yoursplash.domain.usecase.InsertSearchHistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val insertSearchHistoryUseCase: InsertSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase,
    private val getRecentSearchHistoryUseCase: GetRecentSearchHistoryUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val state = _state
        .onStart { observeSearchHistory() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _state.value
        )

    private fun observeSearchHistory() {
        viewModelScope.launch {
            getRecentSearchHistoryUseCase().collect { list ->
                _state.update {
                    it.copy(
                        searchHistory = list
                    )
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _state.update {
            it.copy(searchQuery = query)
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